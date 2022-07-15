package com.example.andersenhw.data.remote

import com.example.andersenhw.data.model.CatFactPageJson
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiRest {
    @GET("/facts")
    suspend fun getCatFactPage(@Query("page") page: Int): CatFactPageJson
}