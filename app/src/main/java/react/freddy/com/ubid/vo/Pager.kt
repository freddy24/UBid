package react.freddy.com.ubid.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
@Entity(
    primaryKeys = ["pageNumber"]
)
data class Pager(
    @field:SerializedName("first")
    val first: Boolean,
    @field:SerializedName("last")
    val last: Boolean,
    @field:SerializedName("pageCount")
    val pageCount: Int,
    @field:SerializedName("pageNumber")
    val pageNumber: Int,
    @field:SerializedName("pageSize")
    val pageSize: Int,
    @field:SerializedName("recordCount")
    val recordCount: Int
) {
}