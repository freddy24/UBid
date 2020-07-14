package react.freddy.com.ubid.api

import androidx.lifecycle.LiveData
import react.freddy.com.ubid.vo.EFSBaseResponse
import react.freddy.com.ubid.vo.LoginInfo
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
interface UBidService {

    @POST("uas/v1/user/login")
    fun login(@Body param: HashMap<String, String>): LiveData<ApiResponse<EFSBaseResponse<LoginInfo>>>
}