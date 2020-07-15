package react.freddy.com.ubid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import react.freddy.com.ubid.repository.LoginRepository

/**
 * data :2020/7/15
 * auth :wjp
 * Description :
 */
class ShareViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShareViewModel(loginRepository) as T
    }
}