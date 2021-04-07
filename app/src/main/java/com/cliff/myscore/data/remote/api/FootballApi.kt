package com.cliff.myscore.data.remote.api

import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.FixturesRaw
import com.cliff.myscore.model.LeaguesRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {

    @GET("countries")
    suspend fun fetchListCountries(): CountriesRaw

    @GET("fixtures?live=all")
    suspend fun fetchLiveScoreFixtures(): FixturesRaw

    @GET("fixtures")
    suspend fun fetchSelectedFixtures(@Query("id") id: Int): FixturesRaw

    @GET("leagues")
    suspend fun fetchLeagues(
        @Query("code") code: String,
        @Query("current") boolean: Boolean
    ): LeaguesRaw


}