package com.example.data.repository

import com.example.data.remote.api.DogApiRest
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class FactsRepository @Inject constructor(
    private val catApiRest: DogApiRest
) {

    var currentPage = 0

    suspend fun getCatFacts(): List<String?> = try {
        val response = catApiRest.getDogFactPage(++currentPage)
        response.facts
    } catch (e: HttpException) {
        Timber.e(e)
        emptyList()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Timber.e(e)
        emptyList()
    }
}
