package react.freddy.com.ubid.ui.unbid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import react.freddy.com.ubid.repository.UnBidRepository

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */

class UnbindViewModelFactory(private val unBidRepository: UnBidRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UnbidViewModel(unBidRepository = unBidRepository) as T
    }
}