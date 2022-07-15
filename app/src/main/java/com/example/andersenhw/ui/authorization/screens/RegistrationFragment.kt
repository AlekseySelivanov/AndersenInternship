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
import com.example.andersenhw.databinding.FragmentRegistrationBinding
import com.example.andersenhw.ui.authorization.model.AuthenticationFormState
import com.example.andersenhw.ui.authorization.model.RegistrationFormEvent
import com.example.common.getColorByAttribute
import com.example.common.launchWhenStarted
import com.example.common.updateText
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance(): RegistrationFragment = RegistrationFragment()
    }

    private val viewModel: AuthenticationViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private var _binding: FragmentRegistrationBinding? = null
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
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() = with(binding) {
        btnLogin.setOnClickListener { viewModel.onRegistrationFormEvent(RegistrationFormEvent.Submit) }

        tvLogin.setOnClickListener { viewModel.onRegistrationFormEvent(RegistrationFormEvent.LoginSelected) }
        tieUserName.doAfterTextChanged {
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.NameChanged(it.toString()))
        }

        tieUserEmail.doAfterTextChanged {
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.EmailChanged(it.toString()))
        }

        tieUserPhone.doAfterTextChanged {
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.PhoneChanged(it.toString()))
        }

        tieUserPassword.doAfterTextChanged {
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.PasswordChanged(it.toString()))
        }

        tieRepeatedPassword.doAfterTextChanged {
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.RepeatedPasswordChanged(it.toString()))
        }

        ternsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onRegistrationFormEvent(RegistrationFormEvent.TernsAgreed(isChecked))
        }
    }

    private fun setupObservers() {
        viewModel.registrationState.onEach { state ->
            updateRegistrationState(state)
        }.launchWhenStarted(lifecycleScope)
        with(lifecycleScope) {
            viewModel.registerSuccess.onEach {
                Timber.d("Register success!")
                showSnackBar(
                    resources.getString(R.string.login_register_success),
                    requireContext().getColorByAttribute(androidx.appcompat.R.attr.colorPrimary),
                    object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            viewModel.onRegistrationSnackBarDismissed()
                        }
                    }
                )
            }.launchIn(this)
            viewModel.navigateToApp.onEach {
                viewModel.onRegistrationSnackBarDismissed()
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

    private fun updateRegistrationState(state: AuthenticationFormState) = with(binding) {
        tieUserName.updateText(state.fullName)
        tieUserEmail.updateText(state.email)
        tieUserPhone.updateText(state.phoneNumber)
        tieUserPassword.updateText(state.password)
        tieRepeatedPassword.updateText(state.repeatedPassword)

        btnLogin.isEnabled = state.ternsAgreed

        tilUseName.error = state.fullNameError
        tilUserEmail.error = state.emailError
        tilUserPhone.error = state.phoneNumberError
        tilUserPassword.error = state.passwordError
        tilRepeatedPassword.error = state.repeatedPasswordError
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}