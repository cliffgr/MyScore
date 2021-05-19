package com.cliff.myscore.di

import com.cliff.myscore.BuildConfig
import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {


    @Provides
    fun FootballApi(retrofit: Retrofit): FootballApi {
        return retrofit.create(FootballApi::class.java)
    }

    @Provides
    fun providesOkhttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .addHeader("x-rapidapi-key", "1778e36d7f134097098abbf4a584ec15")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        providesOkhttpInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(providesOkhttpInterceptor)
            .build()
    }

    @Provides
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}