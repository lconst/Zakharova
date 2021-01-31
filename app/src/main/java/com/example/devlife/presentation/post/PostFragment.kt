package com.example.devlife.presentation.post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.devlife.Navigator
import com.example.devlife.PostApp
import com.example.devlife.R
import com.example.devlife.databinding.FragmentPostBinding
import com.example.devlife.model.entities.Post
import java.lang.IllegalStateException

class PostFragment: Fragment(R.layout.fragment_post) {

    private var _navigator: Navigator? = null
    private val navigator get() = _navigator!!

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory(
            (requireActivity().application as PostApp).cache,
            (requireActivity().application as PostApp).networkModule.repository
        )
    }

    private val progressLoader by lazy { createProgressLoader() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigator) {
            _navigator = context
        } else {
            throw IllegalStateException("Activity must implement Navigator")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        setListeners()
        setObservers()
        viewModel.nextPost()

        return binding.root
    }

    private fun setObservers() {
        viewModel.post.observe(viewLifecycleOwner) { handleOnReadyPost(it) }
        viewModel.hasPrev.observe(viewLifecycleOwner) { handleOnPrevious(it) }
        viewModel.postState.observe(viewLifecycleOwner) { handleOnLoading(it) }
    }

    private fun setListeners() {
        binding.next.setOnClickListener { viewModel.nextPost() }
        binding.prev.setOnClickListener { viewModel.prev() }
    }

    private fun handleOnLoading(state: PostState) {
        when (state) {
            PostState.Loading -> {
                binding.progress.visibility = View.VISIBLE
                binding.image.visibility = View.INVISIBLE
                binding.description.visibility = View.INVISIBLE
                binding.next.visibility = View.INVISIBLE
            }
            PostState.Ready -> {
                binding.progress.visibility = View.INVISIBLE
                binding.image.visibility = View.VISIBLE
                binding.description.visibility = View.VISIBLE
                binding.next.visibility = View.VISIBLE
            }
            PostState.Error -> { handleError() }
        }
    }

    private fun handleOnPrevious(hasPrev: Boolean) {
        val visible = if(hasPrev) View.VISIBLE else View.INVISIBLE
        binding.prev.visibility = visible
    }

    private fun handleOnReadyPost(it: Post) {
        Glide
            .with(this)
            .load(it.gifURL)
            .placeholder(progressLoader)
            .fitCenter()
            .transform(RoundedCorners(roundingRadius))
            .into(binding.image)

        binding.description.text = it.description
    }

    private fun handleError() {
        navigator.navigateToError()
    }

    private fun createProgressLoader() = CircularProgressDrawable(requireContext()).also {
        it.strokeWidth = 5f
        it.centerRadius = 30f
        it.start()
    }

    override fun onDetach() {
        super.onDetach()
        _navigator = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val roundingRadius = 10

        fun newInstance() = PostFragment()

    }
}