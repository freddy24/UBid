package react.freddy.com.ubid.ui.dragger

import dagger.Component

/**
 * data :2020/7/9
 * auth :wjp
 * Description :
 */
@Component(modules = [XiaochiModule::class])
interface XiaochiComponent {

    fun provideGz(): GuaZi

    fun provideHuotuichang(): Huotuichang
}