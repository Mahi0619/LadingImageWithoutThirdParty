package com.mrdev.androidtestassesment.data.network

import com.mrdev.androidtestassesment.post.postModel.PostBean
import retrofit2.http.*


/**
 * Changes has been done on 21 March 2024 by MrDev(Mahesh)
 *
 * */
interface ApiService {

    @GET("media-coverages")
    suspend fun getPostsList(@Query("limit") page: Int): List<PostBean>
}







