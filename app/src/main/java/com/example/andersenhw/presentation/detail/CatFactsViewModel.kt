package com.example.andersenhw.presentation.detail

import androidx.lifecycle.*
import androidx.paging.*
import com.example.andersenhw.data.repository.CatFactRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatFactsViewModel @Inject constructor (
    catFactRepo: CatFactRepo
) : ViewModel() {

    val pagerNoRemoteMediator = catFactRepo
        .getCatFactPagerWithoutRemoteMediator()
        .cachedIn(viewModelScope)

}