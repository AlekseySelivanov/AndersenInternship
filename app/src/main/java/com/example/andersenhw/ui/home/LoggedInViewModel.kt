package com.example.andersenhw.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.authorization.user_data.LogOutUseCase
import com.example.domain.use_cases.authorization.user_data.ObserveLoggedInUserUseCase
import com.example.domain.use_cases.facts.GetCatFactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoggedInViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val observeLoggedInUserUseCase: ObserveLoggedInUserUseCase,
    private val getCatFactsUseCase: GetCatFactsUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    private val _dogFacts = MutableStateFlow<List<String?>>(listOf())
    val dogFacts = _dogFacts.asStateFlow()

    init {
        readName()
        readFacts()
    }

    private fun readName() = observeLoggedInUserUseCase().onEach { user ->
        Timber.d(user?.name)
        user?.name?.let { _name.value = it }
    }.launchIn(viewModelScope)

    fun readFacts() = getCatFactsUseCase()
        .onEach { result ->
            _dogFacts.value = _dogFacts.value + result
        }
        .flowOn(Dispatchers.IO)
        .launchIn(viewModelScope)

    fun logOutClicked() =  viewModelScope.launch {
        logOutUseCase()
        Timber.d("Logout clicked invoked")
    }
}