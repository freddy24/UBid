package react.freddy.com.ubid

import android.app.Application
import timber.log.Timber

/**
 * data :2020/7/10
 * auth :wjp
 * Description :
 */
class UBidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(CrashReportingTree)
        }
    }


}

object CrashReportingTree : Timber.Tree(){
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //TODO
    }
}