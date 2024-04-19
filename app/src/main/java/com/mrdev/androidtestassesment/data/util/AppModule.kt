package com.mrdev.androidtestassesment.data.util


import com.mrdev.androidtestassesment.base.HiltPostItemClickListener
import com.mrdev.androidtestassesment.post.postModel.PostBean

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideOnItemClickListener(): HiltPostItemClickListener<PostBean> {
        return object : HiltPostItemClickListener<PostBean> {
            override fun onPostItemClick(item: PostBean) {
                // Toaster.shortToast(item.body)
            }
        }
    }
}
