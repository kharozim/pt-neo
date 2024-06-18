package com.ozimos.baseproject.data.remote.api

import android.content.Context
import com.ozimos.baseproject.BuildConfig
import com.ozimos.baseproject.util.Constants
import com.ozimos.baseproject.util.SharedPreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.math.log

@Module
@InstallIn(SingletonComponent::class)
object NetworkUtil {


    @Provides
    @Singleton
    fun getOkhttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val baseUrl = getBaseUrl(context)

        val builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

            .addInterceptor { chain ->
                val origin = chain.request()
                val request = origin.newBuilder()
                request.addHeader("Accept", "application/json")

                if (baseUrl == Constants.BASE_URL_GITHUB_USER) {
                    request.addHeader("Accept", "application/vnd.github+json")
                    request.addHeader("Authorization", "Bearer " + BuildConfig.TOKEN_GITHUB)
                }

                chain.proceed(request.build())
            }
            .build()

        return builder
    }


    @Provides
    fun getRetrofit(client: OkHttpClient, @ApplicationContext context: Context): Retrofit {

        val baseUrl = getBaseUrl(context)

        val converterFactory = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit
    }

    private fun getBaseUrl(context: Context): String {
        return SharedPreferenceUtil.getString(context, Constants.KEY_BASE_URL) ?: ""
    }
}