package react.freddy.com.ubid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import react.freddy.com.ubid.data.BASE_EFS_URL
import react.freddy.com.ubid.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * data :2020/7/14
 * auth :wjp
 * Description :
 */
class AppRetrofit {

    fun provideUBidService(): UBidService{
        return Retrofit.Builder()
            .client(initBuilder().build())
            .baseUrl(BASE_EFS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(UBidService::class.java)
    }

    private fun initBuilder(): OkHttpClient.Builder{
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
        return builder
    }

    companion object{

        @Volatile
        private var instance: AppRetrofit? = null

        fun getInstance(): AppRetrofit{
            return instance ?: synchronized(this){
                instance ?: AppRetrofit().also {
                    instance = it
                }
            }
        }
    }
}