package com.mrdev.androidtestassesment.data.network

import com.google.gson.GsonBuilder
import com.mrdev.androidtestassesment.base.LogoutInterface
import com.mrdev.androidtestassesment.other.Cons

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Changes has been done on 21 March 2024 by MrDev(Mahesh)
 *
 * */

object RetrofitProvider {
    private var mLogoutInterface: LogoutInterface? = null

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder().create()
        Retrofit.Builder()
            .baseUrl(Cons.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getRegister())  // Fixed method call
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private fun getRegister(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.authenticator(TokenAuthenticator(mLogoutInterface)) // Pass the LogoutInterface to TokenAuthenticator
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build()
                chain.proceed(request)
            })
        return httpClient.build()
    }
}





