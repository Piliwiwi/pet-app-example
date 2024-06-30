package com.example.network.retrofit

import android.content.Context
import com.example.network.config.WebServiceConfig
import com.example.network.interceptor.LocalInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalWebService<TRetrofitWebService> {

    fun create(
        tClass: Class<TRetrofitWebService>,
        context: Context?,
        hostUrl: String = WebServiceConfig.Url.LOCAL_HOST
    ): TRetrofitWebService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor(context))
        return createRetrofit(
            okHttpClient = okHttpClient,
            tClass = tClass,
            baseUrl = hostUrl
        )
    }

    private fun makeOkHttpClient(dummyInterceptor: LocalInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().also {
            it.addInterceptor(dummyInterceptor)
            flipperInterceptor?.let { flipper ->
                it.addInterceptor(flipper)
            }
        }
        return builder.build()
    }

    private fun makeLoggingInterceptor(context: Context?) = LocalInterceptor(context)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String
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