package react.freddy.com.ubid.ui.dragger

import dagger.Module
import dagger.Provides

/**
 * data :2020/7/7
 * auth :wjp
 * Description :
 */
@Module
class ShangjiaModule {

    @Provides
    fun provideBaozi(): BaoZi{
        return BaoZi("豆沙包")
    }

    @Provides
    fun provideNoodle(): Noodle{
        return Noodle()
    }
}