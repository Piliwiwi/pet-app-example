package com.example.network.retrofit

import android.content.Context
import com.example.cache.sharedpreferences.DPSharedPreferences
import com.example.network.config.WebServiceConfig
import com.example.network.interceptor.TokenInterceptor
import com.example.network.security.PassportTokenManager
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteWebService<TRetrofitWebService> {

    fun create(
        context: Context?,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String,
    ): TRetrofitWebService {
        val okHttpClient = makeOkHttpClient(
            httpLoggingInterceptor = makeLoggingInterceptor(),
            tokenInterceptor = TokenInterceptor(PassportTokenManager(DPSharedPreferences(context).sharedPreferences))
        )
        return createRetrofit(
            okHttpClient = okHttpClient,
            tClass = tClass,
            baseUrl = baseUrl
        )
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().also {
            it.addInterceptor(tokenInterceptor)
            it.addInterceptor(httpLoggingInterceptor)
            it.socketFactory(SocketFactory.getDefault())
            it.connectTimeout(WebServiceConfig.Timeout.CONNECT, TimeUnit.SECONDS)
            it.callTimeout(WebServiceConfig.Timeout.CONNECT, TimeUnit.SECONDS)

            flipperInterceptor?.let { flipper ->
                it.addInterceptor(flipper)
            }
        }
        return builder.build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String,
    ): TRetrofitWebService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(tClass)

    companion object {
        var flipperInterceptor: Interceptor? = null
    }
}