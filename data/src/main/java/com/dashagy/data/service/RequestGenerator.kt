package com.dashagy.data.service

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestGenerator(private val context: Context) {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(ChuckerInterceptor(context))
        .addInterceptor { chain ->
            val defaultRequest = chain.request()

            val defaultHttpUrl = defaultRequest.url()

            val httpUrl = defaultHttpUrl.newBuilder().build()

            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)
                .addHeader(Companion.HEADER_HOST_ARG, Companion.HEADER_HOST_ARG_VAL)
                .addHeader(Companion.API_KEY_ARG, Companion.API_KEY_ARG_VAL)

            chain.proceed(requestBuilder.build())
        }
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl(Companion.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }

    companion object {
        private const val API_BASE_URL = "https://v3.football.api-sports.io"
        private const val API_KEY_ARG = "x-rapidapi-key"
        private const val API_KEY_ARG_VAL = "a3c2dd083c3eb21a4696d3e9876e9451"
        private const val HEADER_HOST_ARG = "x-rapidapi-host"
        private const val HEADER_HOST_ARG_VAL = "v3.football.api-sports.io"
    }

}