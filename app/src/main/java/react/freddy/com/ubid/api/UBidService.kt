package react.freddy.com.ubid.api

import androidx.lifecycle.LiveData
import react.freddy.com.ubid.vo.EFSBaseResponse
import react.freddy.com.ubid.vo.FunctionVo
import react.freddy.com.ubid.vo.LoginInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
interface UBidService {

    @POST("uas/v1/user/login")
    fun login(@Body param: HashMap<String, String>): LiveData<ApiResponse<EFSBaseResponse<LoginInfo>>>

    @GET("uas/v1/sysmgr/users/{userId}/functions")
    fun getFunctions(@Path("userId") userId: String): LiveData<ApiResponse<EFSBaseResponse<List<FunctionVo>>>>
}