package react.freddy.com.ubid.ui.dragger

import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * data :2020/7/9
 * auth :wjp
 * Description :
 */
@Module
class FoodModule {

    @Provides
    @Named("baozi")
    fun provideBaozi(): BaoZi{
        return BaoZi()
    }

    @Provides
    fun provideNoodle(): Noodle{
        return Tongyi()
    }
}