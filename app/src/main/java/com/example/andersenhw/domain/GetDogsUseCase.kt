package com.example.andersenhw.domain

import com.example.andersenhw.data.repository.DogsRepository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(
    private val repository: DogsRepository
) : UseCase<List<String>, Unit?> {

    override suspend fun run(params: Unit?): Result<List<String>> {
        return repository.getDogs()
    }
}