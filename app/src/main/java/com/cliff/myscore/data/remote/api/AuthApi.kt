package com.cliff.myscore.data.remote.api

import com.cliff.myscore.model.TokenRaw
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded","Authorization: Basic *")
    @POST("token")
    suspend fun fetchToken(@Field("grant_type") grant_type: String): TokenRaw
}