package react.freddy.com.ubid.ui.dragger

import dagger.Component
import react.freddy.com.ubid.ui.home.HomeFragment

/**
 * data :2020/7/8
 * auth :wjp
 * Description :
 */
@Component(modules = [TestCreate::class])
interface TestCreateComponent {
    fun ceshi(): Test

}