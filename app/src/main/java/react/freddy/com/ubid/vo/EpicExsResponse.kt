package react.freddy.com.ubid.vo

import com.google.gson.annotations.SerializedName

/**
 * data :2020/7/16
 * auth :WJP
 * Description :
 */
data class EpicExsResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Data,
    @SerializedName("err")
    val err: String?
) {
    data class Data(
        @SerializedName("list")
        val list: List<EpicVo>,
        @SerializedName("pager")
        val pager: Pager
    )
}