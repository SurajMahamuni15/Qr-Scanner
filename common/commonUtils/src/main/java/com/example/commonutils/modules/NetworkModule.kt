package com.example.commonutils.modules

import com.example.commonutils.apis.RetrofitApi
import com.example.commonutils.helper.NullOnEmptyConverterFactory
import com.example.commonutils.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .baseUrl(Constants.BASE_URI)
    }

//    @Singleton
//    @Provides
//    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
//        return OkHttpClient.Builder().connectTimeout(200, TimeUnit.SECONDS)
//            .writeTimeout(200, TimeUnit.SECONDS)
//            .readTimeout(300, TimeUnit.SECONDS).addInterceptor(interceptor).build()
//    }

    @Singleton
    @Provides
    fun providesLoginAPI(
        retrofitBuilder: Retrofit.Builder,
    ): RetrofitApi {
        return retrofitBuilder.build().create(RetrofitApi::class.java)
    }

}