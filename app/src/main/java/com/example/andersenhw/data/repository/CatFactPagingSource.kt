package com.example.andersenhw.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.andersenhw.data.model.CatFact
import com.example.andersenhw.data.remote.CatApiRest
import okio.IOException
import retrofit2.HttpException

private const val CAT_FACTS_STARTING_PAGE_INDEX = 1

class CatFactPagingSource(
    private val catApiRest: CatApiRest,
    private val pageNumberUpdater: (page: Int)->Unit
    ) : PagingSource<Int, CatFact>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatFact> {
        try {
            val position = params.key ?: CAT_FACTS_STARTING_PAGE_INDEX
            pageNumberUpdater(position)

            val response = catApiRest.getCatFactPage(position)
            val catFacts = response.data

            val prevKey = if (position == CAT_FACTS_STARTING_PAGE_INDEX) {
                null
            } else {
                position - 1
            }

            val nextKey = if (catFacts.isEmpty()){
                null
            } else {
                position + 1
            }

            return LoadResult.Page(
                data = catFacts,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatFact>): Int? {
        return null
    }
}