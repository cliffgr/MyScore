package com.cliff.myscore.data.remote

import com.cliff.myscore.data.remote.api.AuthApi
import com.cliff.myscore.data.remote.api.FootballApi
import javax.inject.Inject

class FootballRemoteDataSource @Inject constructor(
    private val footballApi: FootballApi,
    private val authApi: AuthApi
) {
}