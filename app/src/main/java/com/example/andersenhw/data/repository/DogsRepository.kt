package com.example.andersenhw.data.repository

interface DogsRepository {

    suspend fun getDogs(): Result<List<String>>
    suspend fun getImagesByBreed(breed: String): Result<List<String>>
}