package react.freddy.com.ubid.vo

import androidx.room.Entity
import java.util.*

/**
 * data :2020/7/15
 * auth :wjp
 * Description :
 */
@Entity(
    primaryKeys = ["funcId"]
)
data class FunctionVo(
    val funcName: String,
    val funcId: Int,
    val funcUrl: String?,
    val parentId: Int = 0,
    val createTime: Date,
    val orderId: Int,
    val s: String
) {
}