package com.mrdev.androidtestassesment.post

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mrdev.androidtestassesment.data.repository.MainRepository
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.post.postModel.PostBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostVM @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _apiStateFlow: MutableStateFlow<ApiState<List<PostBean>>> =
        MutableStateFlow(ApiState.Empty)
    val apiStateFlow: StateFlow<ApiState<List<PostBean>>> = _apiStateFlow

    //private var limit = 100

    fun getPosts(page: Int ) {
        Log.e("Please wait", "Gonna call you API")
        coroutineScope.launch {
            mainRepository.getPost(page)
                .catch { e ->
                    // Log the error stack trace for more context
                    e.printStackTrace()
                    _apiStateFlow.value = ApiState.Failure(e)
                    Log.e("API_State_Error", "Error: ${e.message}")
                }
                .collect { state ->
                    when (state) {
                        is ApiState.Success<List<PostBean>> -> { // Specify the type parameter User explicitly
                            _apiStateFlow.value = state
                            //val userData: Post = state.data
                            Log.e(
                                "API_State_Success",
                                "Login successful: ${state.data}"
                            )
                            // You can now use 'userData' as the parsed user data
                        }
                        is ApiState.Failure -> {
                            _apiStateFlow.value = state
                            Log.e("API_State_Error", "Error: ${state.error.message}")
                        }
                        else -> {
                            _apiStateFlow.value = state
                            Log.d("API_State", "Loading...")
                        }
                    }
                }
        }
    }
}