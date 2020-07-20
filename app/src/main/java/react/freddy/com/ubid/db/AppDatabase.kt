package react.freddy.com.ubid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import react.freddy.com.ubid.data.DATABASE_NAME
import react.freddy.com.ubid.vo.EpicSearchResult
import react.freddy.com.ubid.vo.EpicVo
import react.freddy.com.ubid.vo.LoginInfo
import react.freddy.com.ubid.workers.UBidDatabaseWorker

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
@Database(entities = [LoginInfo::class, EpicVo::class, EpicSearchResult::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun loginInfoDao(): LoginInfoDao

    abstract fun epicsExDao(): EpicsExDao

    companion object{

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                //将某个非空字段改为空 需要先建立一个临时表 然后copy数据，然后删除临时表
                database.execSQL("CREATE TABLE IF NOT EXISTS EpicVoTemp (biddingDeadline INTEGER NOT NULL, biddingStatus TEXT, epicName Text NOT NULL, id INTEGER PRIMARY KEY NOT NULL, issueNum INTEGER NOT NULL, projectKey TEXT NOT NULL, projectName TEXT NOT NULL, status TEXT NOT NULL)")
                database.execSQL("INSERT INTO EpicVoTemp(biddingDeadline, biddingStatus, epicName, id, issueNum, projectKey, projectName, status) SELECT * FROM epicVo")
                database.execSQL("DROP TABLE EpicVo")
                database.execSQL("ALTER TABLE EpicVoTemp RENAME TO EpicVo")
            }

        }

        private fun buildDatabase(context: Context) : AppDatabase{

            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
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