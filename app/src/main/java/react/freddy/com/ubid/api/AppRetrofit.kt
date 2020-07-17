package react.freddy.com.ubid.api

import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import react.freddy.com.ubid.data.BASE_ITMGR_URL
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
            .baseUrl(BASE_ITMGR_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(UBidService::class.java)
    }

    private fun initBuilder(): OkHttpClient.Builder{
        val token: String? = MMKV.defaultMMKV().decodeString("token")
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestHeader = chain.request().newBuilder()
                    .addHeader("X-Auth", token ?: "")
                    .build()
                chain.proceed(requestHeader)
            }
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