package com.example.andersenhw.data.model

data class ApiModelModel<T>(
    val message: T?,
    val status: String
)

fun ApiModelModel<List<String>>.toListDomainImages(): List<String> {
    return this.message.orEmpty()
}

fun ApiModelModel<HashMap<String, List<String?>>?>.toListDomainDogs(): List<String> {
    val dogs: MutableList<String> = mutableListOf()
    this.message?.keys?.forEach { dogs.add(it) }
    return dogs
}