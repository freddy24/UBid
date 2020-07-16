package react.freddy.com.ubid

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
class AppExecutors(private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor) {

    private constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIO() : Executor{
        return diskIO
    }

    fun networkIO(): Executor{
        return networkIO
    }

    fun mainThread(): Executor{
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(p0: Runnable) {
            mainThreadHandler.post(p0)
        }
    }

    companion object{

        @Volatile private var instance: AppExecutors? = null

        fun getInstance(): AppExecutors{
            return instance ?: synchronized(this){
                instance ?: AppExecutors().also {
                    instance = it
                }
            }
        }
    }
}