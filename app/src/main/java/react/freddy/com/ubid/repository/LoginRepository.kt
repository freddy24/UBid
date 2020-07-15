package react.freddy.com.ubid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import react.freddy.com.ubid.AppExecutors
import react.freddy.com.ubid.api.ApiResponse
import react.freddy.com.ubid.api.UBidService
import react.freddy.com.ubid.db.LoginInfoDao
import react.freddy.com.ubid.util.MD5Util
import react.freddy.com.ubid.vo.EFSBaseResponse
import react.freddy.com.ubid.vo.LoginInfo
import react.freddy.com.ubid.vo.Resource
import java.util.*

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
class LoginRepository(
    private val appExecutors: AppExecutors,
    private val loginInfoDao: LoginInfoDao,
    private val uBidService: UBidService
) {

    fun login(account: String, password: String): LiveData<Resource<LoginInfo>>{
        return object : NetworkBoundResource<LoginInfo,EFSBaseResponse<LoginInfo>>(appExecutors){
            override fun saveCallResult(item: EFSBaseResponse<LoginInfo>) {
                if (item.data != null)
                    loginInfoDao.insert(item.data)
            }

            override fun shouldFetch(data: LoginInfo?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<LoginInfo> {
                return loginInfoDao.findByUserAccount(account)
            }

            override fun createCall(): LiveData<ApiResponse<EFSBaseResponse<LoginInfo>>> {
                val md5Password = MD5Util.genMD5key(password)
                val params = hashMapOf("account" to account, "password" to md5Password)
                return uBidService.login(param = params)
            }

        }.asLiveData()
    }

    fun getLoginInfo(account: String) = loginInfoDao.findByUserAccount(account)

    companion object{

        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance(appExecutors: AppExecutors, loginInfoDao: LoginInfoDao, uBidService: UBidService): LoginRepository{
            return instance ?: synchronized(this){
                instance ?: LoginRepository(appExecutors, loginInfoDao, uBidService).also {
                    instance = it
                }
            }
        }
    }
}