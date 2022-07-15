package com.example.andersenhw.data.model

data class CatFactPageJson(
    val current_page: Int,
    val last_page: Int,
    val data: List<CatFact>
)