package react.freddy.com.ubid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import react.freddy.com.ubid.repository.LoginRepository
import react.freddy.com.ubid.vo.LoginInfo

/**
 * data :2020/7/15
 * auth :wjp
 * Description :
 */
class ShareViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val account: MutableLiveData<String> = MutableLiveData<String>()

    val loginInfo: LiveData<LoginInfo> = account.switchMap {
        loginRepository.getLoginInfo(account = it)
    }

    fun setAccountValue(accountValue: String){
        account.value = accountValue
    }
}