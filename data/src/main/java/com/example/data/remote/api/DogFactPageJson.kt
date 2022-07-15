package com.example.data.remote.api

import com.google.gson.annotations.SerializedName

data class DogFactPageJson(
    val current_page: Int,
    val last_page: Int,
    @SerializedName("facts")
    val facts: List<String>,
)