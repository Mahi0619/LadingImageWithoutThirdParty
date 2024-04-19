package com.mrdev.androidtestassesment.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Response

import javax.inject.Singleton

/**
 * Changes has been done on 21 March 2024 by MrDev(Mahesh)
 *
 * */
@Module
@InstallIn(SingletonComponent::class) // This should be the appropriate component
object NetworkModule {
    @Provides
    @Singleton // Example scope, change as per your requirement
    fun provideApiService(): ApiService {
        return RetrofitProvider.apiService
    }
}


