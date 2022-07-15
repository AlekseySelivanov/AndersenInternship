package com.example.andersenhw.ui.authorization.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.andersenhw.ui.authorization.screens.LoginFragment
import com.example.andersenhw.ui.authorization.screens.RegistrationFragment
import com.example.andersenhw.ui.home.LoggedInFragment
import com.example.andersenhw.ui.splash.SplashFragment

class AuthenticationPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf(RegistrationFragment(), LoginFragment(), LoggedInFragment())

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]

}