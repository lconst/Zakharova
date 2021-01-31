package com.example.devlife.presentation.error

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.devlife.Navigator
import com.example.devlife.databinding.FragmentErrorBinding
import java.lang.IllegalStateException

class ErrorFragment: Fragment() {

    private var _navigator: Navigator? = null
    private val navigator get() = _navigator!!

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        binding.retry.setOnClickListener { navigator.navigateToPost() }

        return binding.root
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
        fun newInstance() = ErrorFragment()
    }
}