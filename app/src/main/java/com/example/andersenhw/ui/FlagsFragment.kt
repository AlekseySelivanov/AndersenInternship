package com.example.andersenhw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andersenhw.databinding.FragmentFlagsBinding

class FlagsFragment : Fragment() {

    private var _binding: FragmentFlagsBinding? = null
    private val binding get() = checkNotNull(_binding) { throw IllegalStateException("Binding is not initialized") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}