package react.freddy.com.ubid.util

import android.content.Context
import react.freddy.com.ubid.AppExecutors
import react.freddy.com.ubid.api.AppRetrofit
import react.freddy.com.ubid.db.AppDatabase
import react.freddy.com.ubid.repository.LoginRepository
import react.freddy.com.ubid.ui.ShareViewModelFactory
import react.freddy.com.ubid.ui.login.LoginViewModelFactory

/**
 * data :2020/7/14
 * auth :wjp
 * Description :
 */
object InjectorUtils {

    private fun getLoginRepository(context: Context): LoginRepository{
        return LoginRepository.getInstance(AppExecutors.getInstance(),
            AppDatabase.getInstance(context).loginInfoDao(),
            AppRetrofit.getInstance().provideUBidService())
    }

    fun provideLoginRepositoryFactory(context: Context) : LoginViewModelFactory{
        return LoginViewModelFactory(getLoginRepository(context))
    }

    fun provideShareViewModelFactory(context: Context) : ShareViewModelFactory{
        return ShareViewModelFactory(getLoginRepository(context))
    }
}