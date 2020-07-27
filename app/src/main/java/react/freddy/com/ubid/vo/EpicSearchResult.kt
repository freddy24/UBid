package react.freddy.com.ubid.vo

import androidx.room.Entity
import androidx.room.TypeConverters

/**
 * data :2020/7/16
 * auth :wjp
 * Description :
 */
@Entity(
    primaryKeys = ["currentPage"]
)
@TypeConverters(UBidConverters::class)
data class EpicSearchResult(
    val epicIds: List<Int>,
    val totalCount: Int,
    val currentPage: Int,
    val status: String?,
    val last: Boolean
)