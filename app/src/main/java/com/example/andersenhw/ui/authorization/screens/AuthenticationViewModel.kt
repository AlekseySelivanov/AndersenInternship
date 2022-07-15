package com.example.andersenhw.ui.authorization.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenhw.ui.authorization.model.AuthenticationFormState
import com.example.andersenhw.ui.authorization.model.LoginFormEvent
import com.example.andersenhw.ui.authorization.model.RegistrationFormEvent
import com.example.domain.use_cases.authorization.user_data.LoginUserUseCase
import com.example.domain.use_cases.authorization.user_data.RegisterUserUseCase
import com.example.domain.use_cases.common.ValidationCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
    private val validationCases: ValidationCases,
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {


    private val _authorizationEventChannel =
        MutableStateFlow<AuthorizationEvent>(AuthorizationEvent.Idle)
    val authorizationEventChannel = _authorizationEventChannel.asStateFlow()

    private val _selectedPage = MutableStateFlow(value = 0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _registrationState = MutableStateFlow(AuthenticationFormState())
    val registrationState = _registrationState.asStateFlow()

    private val _loginState = MutableStateFlow(AuthenticationFormState())
    val loginState = _loginState.asStateFlow()

    private val _navigateToApp = MutableSharedFlow<Unit>()
    val navigateToApp: Flow<Unit> = _navigateToApp

    private val _registerSuccess = MutableSharedFlow<Unit>()
    val registerSuccess: Flow<Unit> = _registerSuccess

    fun onRegistrationFormEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.NameChanged -> {
                _registrationState.value = _registrationState.value.copy(fullName = event.name)
            }
            is RegistrationFormEvent.EmailChanged -> {
                _registrationState.value = _registrationState.value.copy(email = event.email)
            }
            is RegistrationFormEvent.PhoneChanged -> {
                _registrationState.value = _registrationState.value.copy(phoneNumber = event.phone)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                _registrationState.value = _registrationState.value.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                _registrationState.value =
                    _registrationState.value.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.TernsAgreed -> _registrationState.value =
                _registrationState.value.copy(ternsAgreed = event.agreed)

            RegistrationFormEvent.LoginSelected -> _selectedPage.value = 1
            RegistrationFormEvent.Submit -> submitRegistrationData()
        }
    }

    fun onLoginFormEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.NameChanged -> {
                _loginState.value = _loginState.value.copy(fullName = event.name)
            }
            is LoginFormEvent.PasswordChanged -> {
                _loginState.value = _loginState.value.copy(password = event.password)
            }
            LoginFormEvent.RegistrationSelected -> _selectedPage.value = 0
            LoginFormEvent.Submit -> submitLoginData()
        }
    }

    private fun submitRegistrationData() = viewModelScope.launch(Dispatchers.IO) {
        _authorizationEventChannel.value = AuthorizationEvent.Loading

        val nameResult = validationCases.validateFullName(_registrationState.value.fullName)
//        val emailResult = validationCases.validateEmail(_registrationState.value.email)
//        val phoneResult = validationCases.validatePhoneNumber(_registrationState.value.phoneNumber)
        val passwordResult = validationCases.validatePassword(_registrationState.value.password)
//        val repeatedPasswordResult = validationCases.validateRepeatedPassword(
//            _registrationState.value.password,
//            _registrationState.value.repeatedPassword
//        )

        _registrationState.value = _registrationState.value.copy(
            fullNameError = nameResult.errorMessage,
            //          emailError = emailResult.errorMessage,
            //         phoneNumberError = phoneResult.errorMessage,
            passwordError = passwordResult.errorMessage,
//            repeatedPasswordError = repeatedPasswordResult.errorMessage
        )

        val hasError = listOf(
            nameResult,
            //          emailResult,
            passwordResult,
//            repeatedPasswordResult,
            //          phoneResult
        ).any { !it.successful }

        if (hasError) {
            _authorizationEventChannel.value = AuthorizationEvent.Idle
            return@launch
        }

        registerUser(
            name = _registrationState.value.fullName,
            password = _registrationState.value.password
        )
    }


    private fun submitLoginData() = viewModelScope.launch(Dispatchers.IO) {
        _authorizationEventChannel.value = AuthorizationEvent.Loading
        val nameResult = validationCases.validateFullName(_loginState.value.fullName)
        val passwordResult = validationCases.validatePassword(_loginState.value.password)

        _loginState.value = _loginState.value.copy(
            fullNameError = nameResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )

        val hasError = listOf(nameResult, passwordResult).any { !it.successful }
        if (hasError) {
            _authorizationEventChannel.value = AuthorizationEvent.Idle
            return@launch
        }
        loginUser(name = _loginState.value.fullName, password = _loginState.value.password)
    }


    private fun registerUser(name: String, password: String) {
        viewModelScope.launch {
            when (registerUserUseCase(name, password)) {
                RegisterUserUseCase.Result.Failure -> {
                    _authorizationEventChannel.value =
                        AuthorizationEvent.Error("Такой пользователь уже существует")
                }
                RegisterUserUseCase.Result.Success -> {
                    loginUserUseCase(name, password)
                    _registerSuccess.emit(Unit)
                }
            }
        }
    }

    private fun loginUser(name: String, password: String) {
        viewModelScope.launch {
            when (loginUserUseCase(name, password)) {
                LoginUserUseCase.Result.Failure -> {
                    _authorizationEventChannel.value =
                        AuthorizationEvent.Error("Пользователь не найден")
                }
                LoginUserUseCase.Result.Success -> _navigateToApp.emit(Unit)
            }
        }

    }

    fun onRegistrationSnackBarDismissed() {
        viewModelScope.launch {
            _selectedPage.value = 2
            _authorizationEventChannel.value = AuthorizationEvent.Idle
        }
    }

    sealed class AuthorizationEvent {
        object Success : AuthorizationEvent()
        object Loading : AuthorizationEvent()
        object Idle : AuthorizationEvent()
        data class Error(val error: String) : AuthorizationEvent()
    }
}