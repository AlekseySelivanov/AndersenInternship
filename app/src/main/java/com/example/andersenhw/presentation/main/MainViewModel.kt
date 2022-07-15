package com.example.andersenhw.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andersenhw.R
import com.example.andersenhw.domain.DefaultUseCaseExecutor
import com.example.andersenhw.domain.GetDogsUseCase
import com.example.andersenhw.domain.UseCaseExecutor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    useCase: GetDogsUseCase
) : ViewModel(), UseCaseExecutor by DefaultUseCaseExecutor() {
    private val _mainStateView = MutableLiveData<MainStateView>()
    val mainStateView: LiveData<MainStateView> = _mainStateView

    init {
        useCase(viewModelScope, Unit) {
            it.onSuccess { dogs ->
                _mainStateView.value = MainStateView(dogs = dogs)
            }.onFailure {
                _mainStateView.value = MainStateView(error = R.string.message_error)
            }
        }
    }
}