package react.freddy.com.ubid.vo

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
@Entity(
    primaryKeys = ["id"]
)
data class EpicVo(
    @field:SerializedName("biddingDeadline")
    val biddingDeadline: Long,
    @field:SerializedName("biddingStatus")
    val biddingStatus: String? = "",
    @field:SerializedName("epicName")
    val epicName: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("issueNum")
    val issueNum: Int,
    @field:SerializedName("projectKey")
    val projectKey: String,
    @field:SerializedName("projectName")
    val projectName: String,
    @field:SerializedName("status")
    val status: String
) {
}