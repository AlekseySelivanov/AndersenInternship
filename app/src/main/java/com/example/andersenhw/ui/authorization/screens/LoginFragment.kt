package com.example.andersenhw.ui.authorization.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.andersenhw.FinalApp
import com.example.andersenhw.R
import com.example.andersenhw.databinding.FragmentLoginBinding
import com.example.andersenhw.ui.authorization.model.AuthenticationFormState
import com.example.andersenhw.ui.authorization.model.LoginFormEvent
import com.example.common.getColorByAttribute
import com.example.common.launchWhenStarted
import com.example.common.updateText
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginFragment : Fragment() {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private val viewModel: AuthenticationViewModel by viewModels(ownerProducer = {requireParentFragment()})

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as FinalApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() = with(binding) {
        tieUserName.doAfterTextChanged {
            viewModel.onLoginFormEvent(LoginFormEvent.NameChanged(it.toString()))
        }

        tieUserPassword.doAfterTextChanged {
            viewModel.onLoginFormEvent(LoginFormEvent.PasswordChanged(it.toString()))
        }

        btnLogin.setOnClickListener { viewModel.onLoginFormEvent(LoginFormEvent.Submit) }

        tvSignUp.setOnClickListener { viewModel.onLoginFormEvent(LoginFormEvent.RegistrationSelected)
        }
    }

    private fun setupObservers() {
        viewModel.loginState.onEach { state ->
            updateFormState(state)
        }.launchWhenStarted(lifecycleScope)
        with(lifecycleScope){
            viewModel.navigateToApp.onEach {
                showSnackBar(
                    resources.getString(R.string.login_in_success),
                    requireContext().getColorByAttribute(androidx.appcompat.R.attr.colorPrimary),
                    object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            viewModel.onRegistrationSnackBarDismissed()
                        }
                    }
                )
            }.launchIn(this)
        }
    }

    private fun showSnackBar(
        message: String,
        backgroundTint: Int,
        callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null
    ) {
        val snackBar = Snackbar
            .make(
                requireContext(),
                binding.root,
                message,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(backgroundTint)

        if (callback != null) {
            snackBar.addCallback(callback)
        }

        snackBar.show()
    }

    private fun updateFormState(state: AuthenticationFormState) = with(binding) {
        tieUserName.updateText(state.fullName)
        tieUserPassword.updateText(state.password)

        tilUserName.error = state.fullNameError
        tilUserPassword.error = state.passwordError
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}