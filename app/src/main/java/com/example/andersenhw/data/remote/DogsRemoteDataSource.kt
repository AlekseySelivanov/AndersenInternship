package com.example.andersenhw.data.remote

import com.example.andersenhw.commons.NetworkHandler
import com.example.andersenhw.data.model.toListDomainDogs
import com.example.andersenhw.data.model.toListDomainImages
import javax.inject.Inject

class DogsRemoteDataSource @Inject constructor(
    private val dogApiRestRest: DogApiRest,
    override val networkHandler: NetworkHandler
) : DogsDataSource, NetworkService() {

    override suspend fun getDogs(): Result<List<String>> {
        return request { dogApiRestRest.getAllDogs() }
            .map { it.toListDomainDogs() }
    }

    override suspend fun getImagesByBreed(breed: String): Result<List<String>> {
        return request { dogApiRestRest.getImages(breed) }
            .map { model -> model.toListDomainImages() }
    }
}