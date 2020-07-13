package react.freddy.com.ubid.vo

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
@Entity(

)
data class LoginInfo (
    @field:SerializedName("token")
    val token: String,
    @field:SerializedName("fromBoundDevice")
    val fromBoundDevice: Boolean,
    @field:Embedded(prefix = "user_")
    val user: User
){
    data class User(
        @field:SerializedName("userId")
        val userId: Int,
        @field:SerializedName("account")
        val account: String
    )
}