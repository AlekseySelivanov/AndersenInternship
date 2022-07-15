package com.example.andersenhw.ui.authorization.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.andersenhw.FinalApp
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentAuthorizationBinding
import com.example.andersenhw.ui.authorization.adapters.AuthenticationPagerAdapter
import com.example.common.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthorizationFragment : Fragment() {
    private val viewModel: AuthenticationViewModel by viewModels { viewModelFactory }

    companion object {
        fun newInstance(): AuthorizationFragment = AuthorizationFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as FinalApp).appComponent.inject(this)

    }

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPager()
        setupListeners()
        setupObservers()
    }

    private fun initPager() {
        binding.authPager.adapter = AuthenticationPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )
        binding.authPager.isUserInputEnabled = false
    }

    private fun setupListeners() {
        viewModel.selectedPage.onEach { page ->
            binding.authPager.setCurrentItem(page, true)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setupObservers() {
        viewModel.authorizationEventChannel
            .onEach { event ->
                when (event) {
                    is AuthenticationViewModel.AuthorizationEvent.Success -> openHomeScreen()
                    is AuthenticationViewModel.AuthorizationEvent.Error -> showError(event.error)
                    AuthenticationViewModel.AuthorizationEvent.Idle -> showIdle()
                    AuthenticationViewModel.AuthorizationEvent.Loading -> showLoading()
                }
            }
            .launchWhenStarted(lifecycleScope)
    }

    private fun showLoading() {
        binding.authPager.isEnabled = false

        binding.progressIndicator.show()
    }

    private fun showIdle() {
        binding.authPager.isEnabled = true

        binding.progressIndicator.hide()
    }

    private fun showError(error: String) {
        showIdle()

        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun openHomeScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, RegistrationFragment.newInstance())
            .addToBackStack(AuthorizationFragment::class.java.name)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}