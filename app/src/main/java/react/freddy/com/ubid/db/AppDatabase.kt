package react.freddy.com.ubid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import react.freddy.com.ubid.data.DATABASE_NAME
import react.freddy.com.ubid.vo.LoginInfo
import react.freddy.com.ubid.workers.UBidDatabaseWorker

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
@Database(entities = [LoginInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun loginInfoDao(): LoginInfoDao

    companion object{

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) : AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<UBidDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }

}