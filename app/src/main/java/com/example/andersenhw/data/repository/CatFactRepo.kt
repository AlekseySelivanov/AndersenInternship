package com.example.andersenhw.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.andersenhw.data.model.CatFact
import com.example.andersenhw.data.remote.CatApiRest
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class CatFactRepo @Inject constructor (
    private val catApiRest: CatApiRest
) {

    fun getCatFactPagerWithoutRemoteMediator(): Flow<PagingData<CatFact>> =
        Pager(
            config = PagingConfig(pageSize = 1)
        ) {
            CatFactPagingSource(catApiRest){ page ->
                _currentPage.value = "$page"
            }
        }
            .flow

    private val _currentPage = MutableStateFlow("0")
}