package react.freddy.com.ubid.ui.dragger

import dagger.Component
import react.freddy.com.ubid.MainActivity
import react.freddy.com.ubid.ui.home.HomeFragment

/**
 * data :2020/7/7
 * auth :wjp
 * Description :
 */
@Component(modules = [ShangjiaModule::class])
interface QuickPlatform {
    fun waimai(): ZhaiNan

//    fun zhuru(homeFragment: HomeFragment)
}