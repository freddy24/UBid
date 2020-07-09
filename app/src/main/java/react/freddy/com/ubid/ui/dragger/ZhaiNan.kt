package react.freddy.com.ubid.ui.dragger

import java.lang.StringBuilder
import javax.inject.Inject

/**
 * data :2020/7/7
 * auth :wjp
 * Description :
 */
class ZhaiNan {

    @Inject
    lateinit var baoZi: BaoZi

    @Inject
    lateinit var noodle: Noodle

    @Inject
    constructor()

    @Inject
    lateinit var f: String

    fun eat(): String{
        val sb: StringBuilder = StringBuilder()
        sb.append("我从 ")
        sb.append(f.toString())
        sb.append("订的外卖")
        sb.append("我吃的是:")
        sb.append(baoZi.toString())
        sb.append(" ")
        sb.append(noodle.toString())
        return sb.toString()
    }
}