package react.freddy.com.ubid.ui.dragger

import dagger.Component
import react.freddy.com.ubid.ui.test.DaggerTestFragment

/**
 * data :2020/7/9
 * auth :wjp
 * Description :
 */
//@Component(modules = [XiaochiModule::class], dependencies = [XiaochiComponent::class])
@Component
interface FoodComponent {

    fun inject(daggerTestFragment: DaggerTestFragment)

}