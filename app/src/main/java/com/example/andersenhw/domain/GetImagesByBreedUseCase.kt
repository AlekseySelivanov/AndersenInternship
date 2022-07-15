package com.example.andersenhw.domain

import com.example.andersenhw.data.repository.DogsRepository
import javax.inject.Inject

class GetImagesByBreedUseCase @Inject constructor(
    private val repository: DogsRepository
) : UseCase<List<String>, String> {
    override suspend fun run(params: String): Result<List<String>> {
        return repository.getImagesByBreed(params)
    }
}