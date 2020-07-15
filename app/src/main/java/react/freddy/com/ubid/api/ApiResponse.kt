package react.freddy.com.ubid.api

import react.freddy.com.ubid.vo.EFSBaseResponse
import retrofit2.Response

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
sealed class ApiResponse<T> {

    companion object{
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T>{
            if (response.isSuccessful){
                val body = response.body()
                 if (body == null || response.code() == 204){
                    return ApiEmptyResponse()
                }else{
                     val result =  body as EFSBaseResponse<T>
                     val success = result.success
                     val data = result.data
                     if (success && data != null){
                         return ApiSuccessResponse(body = body)
                     }else{
                         val err = result.err
                         return ApiErrorResponse(err ?: "unknow error")
                     }
                }
            }else{
                val msg = response.errorBody()?.toString()
                val errorMsg = if (msg.isNullOrEmpty()){
                    response.message()
                }else{
                    msg
                }
                return ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>(){

}

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()