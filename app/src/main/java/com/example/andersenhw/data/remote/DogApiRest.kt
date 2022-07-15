package com.example.andersenhw.data.remote

import com.example.andersenhw.data.model.ApiModelModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiRest {

    @GET("/api/breeds/list/all")
    suspend fun getAllDogs(): Response<ApiModelModel<HashMap<String, List<String?>>?>>

    @GET("/api/breed/{breed}/images")
    suspend fun getImages(@Path("breed") breed: String): Response<ApiModelModel<List<String>>>
}