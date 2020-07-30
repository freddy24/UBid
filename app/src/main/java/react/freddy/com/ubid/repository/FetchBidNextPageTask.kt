package react.freddy.com.ubid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tencent.mmkv.MMKV
import react.freddy.com.ubid.api.*
import react.freddy.com.ubid.db.AppDatabase
import react.freddy.com.ubid.vo.EpicSearchResult
import react.freddy.com.ubid.vo.Resource
import java.io.IOException

/**
 * data :2020/7/24
 * auth :wjp
 * Description :
 */
class FetchBidNextPageTask constructor(
    private val query: String,
    private val uBidService: UBidService,
    private val db: AppDatabase
) : Runnable{

    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.epicsExDao().findSearchResult(query)
        if (current == null){
            _liveData.postValue(null)
            return
        }
        val nexPage = current.currentPage + 1
        val last = current.last
        if (last){
            _liveData.postValue(Resource.success(false))
            return
        }

        val newValue: Resource<Boolean> = try {
            val mmkv = MMKV.defaultMMKV();
            val token: String? = mmkv.decodeString("token")

            val params: HashMap<String, Any> = hashMapOf("pageNo" to nexPage,
                "biddingStatus" to query,
                "pageSize" to 10)
            val response = uBidService.getEpicsExCall(token, params).execute()
            val apiResponse = ApiResponse.create(response)
            when(apiResponse){
                is ApiSuccessResponse -> {
                    //merge data
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.epicIds)

                    ids.addAll(apiResponse.body.data.list.map { it.id })
                    val merged = EpicSearchResult(
                        epicIds = ids,
                        totalCount = ids.size,
                        currentPage = nexPage,
                        status = query,
                        last = apiResponse.body.data.pager.last
                    )
                    db.runInTransaction {
                        db.epicsExDao().insert(merged)
                        db.epicsExDao().insertEpicVos(apiResponse.body.data.list)
                    }
                    Resource.success(!apiResponse.body.data.pager.last)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }

        }catch (e: IOException){
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}