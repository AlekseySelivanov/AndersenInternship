package com.example.andersenhw.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.andersenhw.databinding.FragmentFlagsBinding

class FlagsFragment : BaseFragment<FragmentFlagsBinding>() {

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFlagsBinding {
        return FragmentFlagsBinding.inflate(inflater, container, false)
    }

}