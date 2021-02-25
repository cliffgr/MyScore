package com.cliff.myscore.data.remote.api

import com.cliff.myscore.model.TokenRaw
import retrofit2.http.POST

interface AuthApi {

    @POST("token")
    suspend fun fetchToken(): TokenRaw
}