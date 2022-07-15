package com.example.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface DogApiRest {

    @GET("facts")
    suspend fun getDogFactPage(@Query("page") page: Int): DogFactPageJson

}