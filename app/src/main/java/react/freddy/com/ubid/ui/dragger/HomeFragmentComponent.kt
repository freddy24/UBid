package react.freddy.com.ubid.ui.dragger

import dagger.Component
import react.freddy.com.ubid.ui.home.HomeFragment
import javax.inject.Singleton

/**
 * data :2020/7/8
 * auth :wjp
 * Description :
 */
@Singleton
@Component(modules = [HomeFragmentModule::class])
interface HomeFragmentComponent {

    fun inject(homeFragment: HomeFragment)
}