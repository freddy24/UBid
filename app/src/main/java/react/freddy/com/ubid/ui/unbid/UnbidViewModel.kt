package react.freddy.com.ubid.ui.unbid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import react.freddy.com.ubid.repository.UnBidRepository
import react.freddy.com.ubid.vo.EpicVo
import react.freddy.com.ubid.vo.Resource

class UnbidViewModel(private val unBidRepository: UnBidRepository) : ViewModel() {

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    val epicsEx: LiveData<Resource<List<EpicVo>>> = _pageNumber.switchMap {
        unBidRepository.loadEpicsEx(it, 10, "Unbidding")
    }

    fun setPageNumberValue(pN: Int){
        _pageNumber.value = pN
    }
}