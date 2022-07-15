package com.example.domain.use_cases.facts

import com.example.data.repository.FactsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCatFactsUseCase @Inject constructor(
    private val factsRepository: FactsRepository
) {

    operator fun invoke() = flow {
        emit(factsRepository.getCatFacts())
    }
}