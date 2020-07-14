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

    lateinit var restaurants: String

    constructor(restaurants: String){
        this.restaurants = restaurants
    }

    @Provides
    fun provideBaozi(): BaoZi{
        return BaoZi()
    }

    @Provides
    fun provideNoodle(tongyi: Tongyi): Noodle{
        return tongyi
    }

    @Provides
    fun provideRestaurants(): String{
        return restaurants
    }
}