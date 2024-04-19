/*
package com.mrdev.androidtestassesment.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.JsonObject
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.adapter.PostAdapter
import kotlinx.coroutines.launch
import java.util.ArrayList


class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private var postVM: PostVM by viewModels()
    private var postList = ArrayList<Posts.PostsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.postModel = postVM
        binding.lifecycleOwner = this
        // setupListeners()
        observeApiState()
        uiSetup()
        setupPostList()
        return binding.root
    }

    private fun uiSetup() {
        val adapter = PostAdapter(postList, onItemClick)
        binding.rcvPost.adapter = adapter
    }

    private fun setupPostList() {
        postList.add(Post)
    }

    private fun observeApiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postVM.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success -> showSuccessState(state)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                    }
                }
            }
        }
    }
    private fun showSuccessState(state: ApiState.Success<JsonObject>) {
        // Handle success state
    }

    private fun showLoadingState() {
        // Show loading state
    }

    private fun showErrorState(error: Throwable) {
        // Show error state
    }

    private fun hideLoadingState() {
        // Hide loading state
    }

    private val onItemClick = object : CallBack<Posts.PostsItem>() {
        override fun onSuccess(t: Posts.PostsItem) {
            // Handle item click
        }

        override fun onError(error: String?) {
            // Handle error
        }
    }
}
*/






/*
package com.mrdev.androidtestassesment.post

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrdev.androidtestassesment.PaginationAdapter
import com.mrdev.androidtestassesment.PaginationScrollListener
import com.mrdev.androidtestassesment.Posts
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.data.network.ApiService
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.safety.data.util.ApiState
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class PostFragment : Fragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private lateinit var postVM: PostVM
    private lateinit var paginationAdapter: PaginationAdapter

    private var postList = ArrayList<Posts.PostsItem>()
    private val PAGE_START = 1
    private var isLoading = false
    private var isLastPage = false
    private val TOTAL_PAGES = 5
    private var currentPage = PAGE_START

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        postVM = ViewModelProvider(this).get(PostVM::class.java)
        uiSetup()
        setupPostList()
    }

    private fun uiSetup() {
        paginationAdapter = PaginationAdapter(requireContext())
        binding.rcvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvPost.adapter = paginationAdapter

        binding.rcvPost.addOnScrollListener(object : PaginationScrollListener(binding.rcvPost.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                this@PostFragment.isLoading = true
                currentPage++
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

       // loadFirstPage()
        postVM.getPlants()
    }

*/
/*    private fun loadNextPage() {

        postVM.getPlants()

        apiService.getPostsList().enqueue(object : Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                paginationAdapter.removeLoadingFooter()
                isLoading = false

                val results = response.body()
                results?.let {
                    paginationAdapter.addAll(it)
                    if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter()
                    else isLastPage = true
                }
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun loadFirstPage() {
        apiService.getPostsList().enqueue(object : Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                binding.progressbar.visibility = View.GONE
                val results = response.body()
                results?.let {
                    paginationAdapter.addAll(it)
                    if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter()
                    else isLastPage = true
                }
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                // Handle failure
            }
        })
    }*//*


    private fun setupPostList() {
        postList.add(Posts.PostsItem(1, 1, "Fun", "Nothing to express"))
    }


    private fun observeApiState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                postVM.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success<Posts> -> showSuccessState(state)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                        else -> {}
                    }
                }
            }
        }
    }

    private fun showSuccessState(state: ApiState.Success<Posts>) {
        paginationAdapter.addAll(state)
        if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter()
        else isLastPage = true
    }


    private fun showLoadingState() {

    }
    private fun showErrorState(error: Throwable) {
      //  Toaster.shortToast(error.message.toString())
    }
    private fun hideLoadingState() {
        // binding.progressBar.isVisible = false
    }

}
*/
