package react.freddy.com.ubid.ui.dragger

import dagger.Module
import dagger.Provides

/**
 * data :2020/7/8
 * auth :wjp
 * Description :
 */
@Module
object TestCreate {

    @Provides
    fun provideTest1(): Int {
        return 1;
    }

    @Provides
    fun provideTest2(): String {
        return "test component create()"
    }

    @Provides
    fun provideTest(): Test {
        return Test()
    }
}