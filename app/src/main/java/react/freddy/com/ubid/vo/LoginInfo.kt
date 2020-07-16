package react.freddy.com.ubid.vo

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * data :2020/7/13
 * auth :wjp
 * Description :
 */
@Entity(
    primaryKeys = ["id"]
)
data class LoginInfo (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("token")
    val token: String,
    @field:SerializedName("fromBoundDevice")
    val fromBoundDevice: Boolean,
    @field:Embedded(prefix = "user_")
    val user: User,
    @field:Embedded(prefix = "person_")
    val person: Person
){
    data class User(
        @field:SerializedName("userId")
        val userId: Int,
        @field:SerializedName("account")
        val account: String
    )

    data class Person(
        @field:SerializedName("name")
        val name: String
    )
}