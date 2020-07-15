package react.freddy.com.ubid.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5工具类
 * Created by wujinpeng on 2017/7/5.
 */
object MD5Util {
    fun genMD5key(key: String): String {
        val cacheKey: String
        cacheKey = try {
            val mDigest = MessageDigest.getInstance("MD5")
            mDigest.update(key.toByteArray())
            bytesToHexString(mDigest.digest())
        } catch (e: NoSuchAlgorithmException) {
            key.hashCode().toString()
        }
        return cacheKey
    }

    private fun bytesToHexString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}