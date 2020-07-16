package react.freddy.com.ubid.api

import androidx.lifecycle.LiveData
import react.freddy.com.ubid.vo.*
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

    @POST("itmgrbidding/v1/itmgr/bidding/getEpicsEx")
    fun getEpicsEx(@Body param: HashMap<String, Any>): LiveData<ApiResponse<EpicExsResponse>>
}