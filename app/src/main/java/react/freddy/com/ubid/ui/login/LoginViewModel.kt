package react.freddy.com.ubid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.switchMap
import react.freddy.com.ubid.data.Result

import react.freddy.com.ubid.R
import react.freddy.com.ubid.repository.LoginRepository
import react.freddy.com.ubid.util.AbsentLiveData
import react.freddy.com.ubid.vo.LoginInfo
import react.freddy.com.ubid.vo.Resource

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private val _userId: MutableLiveData<UserId> = MutableLiveData()
    val userId: LiveData<UserId>
        get() = _userId

    val loginfo: LiveData<Resource<LoginInfo>> = _userId.switchMap{ input ->
        input.ifExist { account, password -> loginRepository.login(account, password)}
    }

    fun setId(account: String, password: String){
        val update = UserId(account, password)
        if (_userId.value == update){
            return
        }
        _userId.value = update
    }

    data class UserId(val account: String, val password: String){

        fun <T> ifExist(u: (String, String) -> LiveData<T>): LiveData<T>{
            return if (account.isBlank() || password.isBlank()){
                 AbsentLiveData.create()
            }else{
                u(account, password)
            }
        }
    }
}