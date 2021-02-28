package com.cliff.myscore.data.remote.api

import com.cliff.myscore.model.CountriesRaw
import retrofit2.http.GET

interface FootballApi {

    @GET("countries")
    suspend fun fetchListCountries(): CountriesRaw

}