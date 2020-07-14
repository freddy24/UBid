package react.freddy.com.ubid.vo

/**
 * data :2020/7/14
 * auth :wjp
 * Description :
 */
data class EFSBaseResponse<T>(var success: Boolean, val code: Int?, val data: T?, val err: String?) {

}