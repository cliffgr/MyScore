package com.cliff.myscore.model

data class CountriesRaw(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: List<Any>,
    val response: List<Response>,
    val results: Int
) {
    data class Paging(
        val current: Int,
        val total: Int
    )

    data class Response(
        val code: String,
        val flag: String,
        val name: String
    )
}