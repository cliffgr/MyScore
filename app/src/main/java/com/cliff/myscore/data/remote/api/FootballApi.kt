package com.cliff.myscore.data.remote.api

import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.FixturesRaw
import retrofit2.http.GET

interface FootballApi {

    @GET("countries")
    suspend fun fetchListCountries(): CountriesRaw

    @GET("fixtures?live=all")
    suspend fun fetchLiveScoreFixtures(): FixturesRaw

}