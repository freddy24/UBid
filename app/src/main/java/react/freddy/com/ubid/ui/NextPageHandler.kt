package react.freddy.com.ubid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import react.freddy.com.ubid.repository.UnBidRepository
import react.freddy.com.ubid.vo.Resource
import react.freddy.com.ubid.vo.Status

/**
 * data :2020/7/24
 * auth :wjp
 * Description :
 */
class NextPageHandler(private val unBidRepository: UnBidRepository) : Observer<Resource<Boolean>> {

    private var nextPageLiveData: LiveData<Resource<Boolean>>? = null
    val loadMoreState = MutableLiveData<LoadMoreState>()
    private var pageNumber: Int? = null
    private var _hasMore: Boolean = false
    val hasMore
        get() = _hasMore

    init {
        reset()
    }

    fun queryNextPage(query: String, pageNumber: Int){
        if (this.pageNumber == pageNumber){
            return
        }

        unregister()
        this.pageNumber = pageNumber
        nextPageLiveData = unBidRepository.getNextPage(query, pageNumber)
        loadMoreState.value = LoadMoreState(
            isRunning = true,
            errorMessage = null
        )
        nextPageLiveData?.observeForever(this)
    }


    override fun onChanged(result: Resource<Boolean>?) {
        if (result == null){
            reset()
        }else{
            when(result.status){
                Status.SUCCESS ->{
                    _hasMore = result.data == true
                    unregister()
                    loadMoreState.value = LoadMoreState(
                        isRunning = false,
                        errorMessage = null
                    )
                }
                Status.ERROR -> {
                    _hasMore = true
                    unregister()
                    loadMoreState.value = LoadMoreState(
                        isRunning = false,
                        errorMessage = result.message
                    )
                }
                Status.LOADING -> {

                }
            }
        }
    }

    private fun unregister(){
        nextPageLiveData?.removeObserver(this)
        nextPageLiveData = null
        if (_hasMore){
            pageNumber = null
        }
    }

    fun reset(){
        unregister()
        _hasMore = true
        loadMoreState.value = LoadMoreState(
            isRunning = false,
            errorMessage = null
        )
    }
}

class LoadMoreState(val isRunning: Boolean, val errorMessage: String?){
    private var handlerError = false

    val errorMessageIfNotHandled: String?
        get() {
            if (handlerError){
                return null
            }
            handlerError = true
            return errorMessage
        }
}