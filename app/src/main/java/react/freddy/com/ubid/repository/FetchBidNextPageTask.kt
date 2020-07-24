package react.freddy.com.ubid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import react.freddy.com.ubid.api.UBidService
import react.freddy.com.ubid.db.AppDatabase
import react.freddy.com.ubid.vo.Resource

/**
 * data :2020/7/24
 * auth :wjp
 * Description :
 */
class FetchBidNextPageTask constructor(
    private val query: String,
    private val next: Int,
    private val uBidService: UBidService,
    private val db: AppDatabase
) : Runnable{

    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.epicsExDao().getSearchResult(next, query)
        if (current == null){
            _liveData.postValue(null)
            return
        }
    }
}