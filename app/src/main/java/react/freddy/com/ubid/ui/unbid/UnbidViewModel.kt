package react.freddy.com.ubid.ui.unbid

import androidx.lifecycle.*
import react.freddy.com.ubid.repository.UnBidRepository
import react.freddy.com.ubid.ui.LoadMoreState
import react.freddy.com.ubid.ui.NextPageHandler
import react.freddy.com.ubid.util.AbsentLiveData
import react.freddy.com.ubid.util.Event
import react.freddy.com.ubid.vo.EpicVo
import react.freddy.com.ubid.vo.Resource

class UnbidViewModel(private val unBidRepository: UnBidRepository) : ViewModel() {

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    private val _queryCondition = MutableLiveData<QueryCondition>()
    val queryCondition: LiveData<QueryCondition> = _queryCondition

    private val nextPageHandler = NextPageHandler(unBidRepository)

    val epicsEx: LiveData<Resource<List<EpicVo>>> = _pageNumber.switchMap {
        unBidRepository.loadEpicsEx(it, 10, "Unbidding")
    }

    val epics: LiveData<Resource<List<EpicVo>>> = _queryCondition.switchMap {
        it.ifExist { type, pageNumber ->
            if (type.isBlank() || pageNumber == 0) {
                AbsentLiveData.create()
            } else {
                unBidRepository.loadEpicsEx(pageNumber, 10, type)
            }
        }
    }

    val isLogin: LiveData<Event<Boolean>> = epics.map { result ->
        var isLogin = false
        isLogin = result.message != "unAuth"
        Event(isLogin)
    }

    fun setPageNumberValue(pN: Int) {
        _pageNumber.value = pN
    }

    fun setQueryConditions(type: String, pageNumber: Int) {
        val con = QueryCondition(type, pageNumber)
        if (_queryCondition.value == con) {
            return
        }
        nextPageHandler.reset()
        _queryCondition.value = con
    }

    fun loadMorePage() {
        _queryCondition.value?.let {
            nextPageHandler.queryNextPage(it.type, it.pageNumber)
        }
    }

    val loadMoreState: LiveData<LoadMoreState>
        get() = nextPageHandler.loadMoreState

    data class QueryCondition(val type: String, val pageNumber: Int) {
        fun <T> ifExist(u: (String, Int) -> LiveData<T>): LiveData<T> {
            return if (type.isBlank() || pageNumber == 0) {
                AbsentLiveData.create()
            } else {
                u(type, pageNumber)
            }
        }
    }
}