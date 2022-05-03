package com.example.appmeli.api

import com.example.appmeli.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    }


    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}