package com.mrdev.androidtestassesment.post.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mrdev.androidtestassesment.databinding.FragmentPostDetailBinding
import com.mrdev.androidtestassesment.post.PostVM
import com.mrdev.androidtestassesment.post.postModel.PostBean

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private val viewModel: PostVM by viewModels()
    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the Post object from arguments
        val post = arguments?.getParcelable<PostBean>("post")
        // Update the UI with the Post data
        binding.textTitle.text = post?.title ?: "N/A"
        binding.textBody.text = post?.language ?: "N/A"
    }

    companion object {
        @JvmStatic
        fun newInstance(post: PostBean) =
            PostDetailFragment().apply {
                arguments = Bundle().apply {
                    // Pass the Post object as an argument
                    putParcelable("post", post)
                }
            }
    }
}

