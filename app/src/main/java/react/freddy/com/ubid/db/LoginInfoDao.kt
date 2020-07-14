package react.freddy.com.ubid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import react.freddy.com.ubid.vo.LoginInfo

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
@Dao
interface LoginInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(loginInfo: LoginInfo)

    @Query("select * from logininfo where user_account = :account")
    fun findByUserAccount(account: String): LiveData<LoginInfo>
}