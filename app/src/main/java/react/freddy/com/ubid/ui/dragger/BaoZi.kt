package react.freddy.com.ubid.ui.dragger

import javax.inject.Inject

/**
 * data :2020/7/7
 * auth :wjp
 * Description :
 */
class BaoZi {

    var name: String = ""

    @Inject
    constructor(name: String){
        this.name = name
    }

    override fun toString(): String {
        return name
    }
}