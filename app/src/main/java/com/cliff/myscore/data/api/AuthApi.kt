package com.cliff.myscore.data.api

import com.cliff.myscore.model.TokenRaw
import retrofit2.http.POST

interface AuthApi {

    @POST("token")
    suspend fun fetchAllPlayLists(): TokenRaw
}