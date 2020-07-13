package react.freddy.com.ubid.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import react.freddy.com.ubid.AppExecutors
import react.freddy.com.ubid.api.UBidService
import react.freddy.com.ubid.data.LoginDataSource
import react.freddy.com.ubid.db.AppDatabase
import react.freddy.com.ubid.db.LoginInfoDao
import react.freddy.com.ubid.repository.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    appExecutors = AppExecutors.getInstance(),
                    loginInfoDao = AppDatabase.getInstance(context).loginInfoDao(),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}