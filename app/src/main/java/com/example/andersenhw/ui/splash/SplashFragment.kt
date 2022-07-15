package com.example.andersenhw.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentSplashBinding
import com.example.andersenhw.ui.authorization.screens.AuthorizationFragment

class SplashFragment : Fragment() {

    companion object {
        fun newInstance(): SplashFragment = SplashFragment()
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigateToScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navigateToScreen() {
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthorizationFragment.newInstance())
                    .addToBackStack(SplashFragment::class.java.name)
                    .commit()
            }

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationRepeat(animation: Animator?) = Unit
        })
    }
}