package com.cliff.myscore.data.remote

import com.cliff.myscore.data.remote.api.AuthApi
import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.TokenRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FootballRemoteDataSource @Inject constructor(
    private val footballApi: FootballApi,
    private val authApi: AuthApi
) {

    suspend fun fetchToken(): Flow<Result<TokenRaw>> {
        return flow {
            emit(Result.success(authApi.fetchToken()))
        }.catch { exception -> emit(Result.failure(exception)) }
    }

}