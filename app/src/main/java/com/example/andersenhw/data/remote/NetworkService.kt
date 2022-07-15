package com.example.andersenhw.data.remote

import com.example.andersenhw.commons.MainExceptions
import com.example.andersenhw.commons.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

abstract class NetworkService {
    abstract val networkHandler: NetworkHandler

    suspend fun <T> request(
        default: T? = null,
        call: suspend () -> Response<T>
    ): Result<T> = withContext(Dispatchers.IO) {
        return@withContext when (networkHandler.isNetworkAvailable()) {
            true -> performRequest(call, default)
            false, null -> Result.failure(MainExceptions.NetworkConnection())
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun <T> performRequest(
        call: suspend () -> Response<T>,
        default: T? = null
    ): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: (default?.let { Result.success(it) } ?: Result.failure(MainExceptions.GeneralError()))
            } else {
                return Result.failure(MainExceptions.GeneralError())
            }
        } catch (exception: Throwable) {
            Timber.d("exception .. $exception")
            Result.failure(MainExceptions.ServerError())
        }
    }
}