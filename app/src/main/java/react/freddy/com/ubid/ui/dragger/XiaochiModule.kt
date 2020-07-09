package react.freddy.com.ubid.ui.dragger

import dagger.Module
import dagger.Provides

/**
 * data :2020/7/9
 * auth :wjp
 * Description :
 */
@Module
class XiaochiModule {

    @Provides
    fun provideGuazi(): GuaZi{
        return GuaZi()
    }

    @Provides
    fun proviceHuotuichang(): Huotuichang{
        return Huotuichang()
    }
}