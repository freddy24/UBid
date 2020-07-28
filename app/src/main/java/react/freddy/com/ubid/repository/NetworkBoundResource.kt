package react.freddy.com.ubid.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import react.freddy.com.ubid.AppExecutors
import react.freddy.com.ubid.api.*
import react.freddy.com.ubid.vo.EFSBaseResponse
import react.freddy.com.ubid.vo.Resource

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors){

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource, Observer { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)){
                fetchFromNetwork(dbSource)
            }else{
                result.addSource(dbSource, Observer { newData ->
                    setValue(Resource.success(newData))
                })
            }
        })
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>){
        if (result.value != newValue){
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()

        result.addSource(dbSource){ newData ->
            setValue(Resource.loading(newData))
        }

        result.addSource(apiResponse){ response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when(response){
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val efsData = handleEFSResponse(response)
                        if (efsData.success){
                            saveCallResult(processResponse(response))
                            appExecutors.mainThread().execute {
                                result.addSource(loadFromDb(), Observer {newData ->
                                    setValue(newValue = Resource.success(newData))
                                })
                            }
                        }else{
                            appExecutors.mainThread().execute {
                                result.addSource(dbSource){ newData ->
                                    setValue(Resource.error(efsData.err ?: "", newData))
                                }
                            }
                        }
                    }
                }

                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb(), Observer { newData ->
                            setValue(newValue = Resource.success(newData))
                        })
                    }
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource){ newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed(){}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun handleEFSResponse(response: ApiSuccessResponse<RequestType>): EFSData
}