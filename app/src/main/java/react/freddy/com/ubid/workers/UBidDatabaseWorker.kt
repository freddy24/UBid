package react.freddy.com.ubid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
class UBidDatabaseWorker(context: Context,
workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
       return coroutineScope {
           //这里可以做一些数据库的初始操作
            Result.success()
        }
    }

}