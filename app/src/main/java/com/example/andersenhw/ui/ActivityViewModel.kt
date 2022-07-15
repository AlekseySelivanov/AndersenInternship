package com.example.andersenhw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.authorization.user_data.ObserveLoggedInUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val observeLoggedInUserUseCase: ObserveLoggedInUserUseCase
): ViewModel() {

    private val _logOut = MutableSharedFlow<Unit>()
    val logOut: Flow<Unit> = _logOut

    init {
        viewModelScope.launch {
            observeLoggedInUserUseCase().onEach { user ->
                if(user == null) {
                    _logOut.emit(Unit)
                }
            }.launchIn(this)
        }
    }
}