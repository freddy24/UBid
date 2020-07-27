package react.freddy.com.ubid.api

import androidx.lifecycle.LiveData
import react.freddy.com.ubid.vo.*
import retrofit2.Call
import retrofit2.http.*

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

    @POST("itmgrbidding/v1/itmgr/bidding/getEpicsEx")
    fun getEpicsEx(@Header("X-Auth") token: String?, @Body param: HashMap<String, Any>): LiveData<ApiResponse<EpicExsResponse>>

    @POST("itmgrbidding/v1/itmgr/bidding/getEpicsEx")
    fun getEpicsExCall(@Header("X-Auth") token: String?, @Body param: HashMap<String, Any>): Call<EpicExsResponse>
}