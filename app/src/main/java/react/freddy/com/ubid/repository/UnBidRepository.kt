package react.freddy.com.ubid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import react.freddy.com.ubid.AppExecutors
import react.freddy.com.ubid.api.ApiResponse
import react.freddy.com.ubid.api.ApiSuccessResponse
import react.freddy.com.ubid.api.EFSData
import react.freddy.com.ubid.api.UBidService
import react.freddy.com.ubid.db.AppDatabase
import react.freddy.com.ubid.db.EpicsExDao
import react.freddy.com.ubid.util.AbsentLiveData
import react.freddy.com.ubid.vo.*
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
class UnBidRepository(private val appExecutors: AppExecutors,
                      private val db: AppDatabase,
                      private val epicsExDao: EpicsExDao,
                      private val uBidService: UBidService) {

    fun loadEpicsEx(pageNo: Int, pageSize: Int, biddingStatus: String): LiveData<Resource<List<EpicVo>>> {
        return object : NetworkBoundResource<List<EpicVo>, EpicExsResponse>(appExecutors){
            override fun saveCallResult(item: EpicExsResponse) {
                val data = item.data
                val epicIds = data.list.map { it.id }
                val epicSearchResult = EpicSearchResult(
                    epicIds = epicIds,
                    totalCount = data.list.size,
                    currentPage = pageNo,
                    status = biddingStatus
                )
                db.runInTransaction {
                    epicsExDao.insert(data.list)
                    Timber.i("insert epicVo success")
                    Timber.i("epicVo info = ${Gson().toJson(data.list)}")
                    Timber.i("epic search result = ${Gson().toJson(epicSearchResult)}")
                    epicsExDao.insert(epicSearchResult)
                }
            }

            override fun shouldFetch(data: List<EpicVo>?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<List<EpicVo>> {
                return epicsExDao.getSearchResult(pageNo, biddingStatus).switchMap { searchData ->
                    if (searchData == null){
                        AbsentLiveData.create()
                    }else{
                        epicsExDao.loadEpics(searchData.epicIds)
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<EpicExsResponse>> {

                val mmkv = MMKV.defaultMMKV();
                val token: String? = mmkv.decodeString("token")

                val params: HashMap<String, Any> = hashMapOf("pageNo" to pageNo,
                "biddingStatus" to biddingStatus,
                "pageSize" to pageSize)
                return uBidService.getEpicsEx(token, params)
            }

            override fun handleEFSResponse(response: ApiSuccessResponse<EpicExsResponse>): EFSData {
                return EFSData(response.body.success, response.body.err)
            }

        }.asLiveData()
    }

    fun getNextPage(query: String, next: Int): LiveData<Resource<Boolean>>{
        val fetchBidNextPageTask = FetchBidNextPageTask(
            query = query,
            next = next,
            uBidService = uBidService,
            db = db
        )
        appExecutors.networkIO().execute(fetchBidNextPageTask)
        return fetchBidNextPageTask.liveData
    }
}