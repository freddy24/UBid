package react.freddy.com.ubid.ui.biding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import react.freddy.com.ubid.repository.UnBidRepository

/**
 * data :2020/7/30
 * auth :wjp
 * Description :
 */
@Suppress("UNCHECKED_CAST")
class BidingViewModelFactory(private val unBidRepository: UnBidRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BidingViewModel(unBidRepository) as T
    }
}