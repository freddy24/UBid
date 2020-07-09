package react.freddy.com.ubid.ui.dragger

import dagger.Component
import dagger.Module
import dagger.Provides
import react.freddy.com.ubid.ui.home.HomeFragment
import javax.inject.Singleton

/**
 * data :2020/7/8
 * auth :wjp
 * Description :
 */
@Module
class HomeFragmentModule {

    @Provides
    @Singleton
    fun provideTestSingleton(): TestSingleton{
        return TestSingleton()
    }
}