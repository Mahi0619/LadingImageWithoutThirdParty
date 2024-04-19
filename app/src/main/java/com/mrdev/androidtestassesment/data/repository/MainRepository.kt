package com.mrdev.androidtestassesment.data.repository

import com.mrdev.androidtestassesment.data.network.ApiService
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.post.postModel.PostBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject


class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPost(page: Int): Flow<ApiState<List<PostBean>>> = flow {
        emit(ApiState.Loading)
        try {
            val observation = apiService.getPostsList(page)
            emit(ApiState.Success(observation))
        } catch (e: Exception) {
            emit(ApiState.Failure(e))
        }
    }

}
