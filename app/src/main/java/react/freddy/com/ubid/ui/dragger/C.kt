package react.freddy.com.ubid.ui.dragger

import javax.inject.Inject

/**
 * data :2020/7/7
 * auth :wjp
 * Description :
 */
class C {
    var d: Int = 0

    var e: String = ""

    @Inject
    constructor(index: Int, value: String){
        this.d = index
        this.e = value
    }
}