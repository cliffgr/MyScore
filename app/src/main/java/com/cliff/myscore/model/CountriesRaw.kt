package com.cliff.myscore.model

data class CountriesRaw(
    val errors: List<Any>,
    val paging: Paging,
    val parameters: List<Any>,
    val response: List<Country>,
    val results: Int
)

data class Paging(
    val current: Int,
    val total: Int
)

data class Country(
    val code: String,
    val flag: String,
    val name: String
)