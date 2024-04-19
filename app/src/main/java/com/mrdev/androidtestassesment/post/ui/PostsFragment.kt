/*
package com.mrdev.androidtestassesment.post.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post
import com.mrdev.androidtestassesment.post.PostVM
import com.mrdev.androidtestassesment.post.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel: PostVM by viewModels()
    private lateinit var binding: FragmentPostBinding
    private val postAdapter by lazy { PostAdapter(ArrayList(), onItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.postModel = viewModel
        binding.lifecycleOwner = this
        binding.rcvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvPost.adapter = postAdapter

        viewModel.getPlants()
        observeApiState()

        return binding.root
    }

    private fun observeApiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success -> showSuccessState(state.data)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                    }
                }
            }
        }
    }

    private fun showSuccessState(data: List<Post>) {
        postAdapter.updateData(data)
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

    private val onItemClick = object : CallBack<Post>() {
        override fun onSuccess(t: Post) {
            // Handle item click
        }

        override fun onError(error: String?) {
            // Handle error
        }
    }
}
*/







package com.mrdev.androidtestassesment.post.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.MainActivity
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.PostVM
import com.mrdev.androidtestassesment.post.adapter.PostAdapter
import com.mrdev.androidtestassesment.post.postModel.PostBean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val viewModel: PostVM by viewModels()
    private lateinit var binding: FragmentPostBinding
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 20
    private var MAX_PAGE_SIZE = 100
    private lateinit var postAdapter: PostAdapter
    private val originalData: ArrayList<PostBean> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setupRecyclerView()
        observeApiState()
        return binding.root
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter(requireContext(), onItemClick,originalData)
        binding.rcvPost.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvPost.adapter = postAdapter

        // Initial data load
        loadInitialData()

        // Pagination setup

        binding.rcvPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rcvPost.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if scrolling downwards and nearing the end of the list
                if (!isLoading && !isLastPage && dy > 0) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadMoreItems()
                    }
                }
            }
        })

    }

    private fun observeApiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success -> showSuccessState(state.data)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                    }
                }
            }
        }
    }

    private fun showSuccessState(data: List<PostBean>) {
        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE
       // postAdapter.updateData(data)
        originalData.addAll(data)
        postAdapter.notifyDataSetChanged()
        isLoading = false
        currentPage += 10 // Increment by 10 for next page
    }

    private fun showLoadingState() {
        binding.progressbar.visibility = View.VISIBLE
        binding.rcvPost.visibility = View.GONE
        isLoading = true
    }

    private fun showErrorState(error: Throwable) {
        isLoading = false
        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE
        // Handle error display
    }

    private fun hideLoadingState() {
        isLoading = false
        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE
    }

    private fun loadInitialData() {
        isLoading = true
        viewModel.getPosts(currentPage)
    }

   /* private fun loadMoreItems() {
        isLoading = true
        viewModel.getPosts(currentPage)
    }*/


    private fun loadMoreItems() {
        if (currentPage <= MAX_PAGE_SIZE) { // Check if current page is within the limit
            isLoading = true
            viewModel.getPosts(currentPage)
        } else {
            // Notify user or handle the case when the maximum page size is reached
            // For example, show a toast message indicating that no more items can be loaded
            Toast.makeText(requireContext(), "Maximum page size reached", Toast.LENGTH_SHORT).show()
        }
    }


    private val onItemClick = object : CallBack<PostBean>() {
        override fun onSuccess(t: PostBean) {
            val frg = (requireActivity() as MainActivity)
            frg.navigateToFragment(PostDetailFragment.newInstance(t))
        }

        override fun onError(error: String?) {
            Toast.makeText(requireContext(), error ?: "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }
}


