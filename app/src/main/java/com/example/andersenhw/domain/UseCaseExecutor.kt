package com.example.andersenhw.domain

import kotlinx.coroutines.*

interface UseCaseExecutor {
    operator fun <T, P> UseCase<T, P>.invoke(
        scope: CoroutineScope,
        params: P,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Result<T>) -> Unit = {}
    )
}

class DefaultUseCaseExecutor : UseCaseExecutor {

    override operator fun <T, P> UseCase<T, P>.invoke(
        scope: CoroutineScope,
        params: P,
        dispatcher: CoroutineDispatcher,
        onResult: (Result<T>) -> Unit
    ) {
        val backgroundJob = scope.async(dispatcher) { run(params) }
        scope.launch {
            onResult(backgroundJob.await())
        }
    }
}
