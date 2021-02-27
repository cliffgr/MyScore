package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.TokenRaw
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: FootballRemoteDataSource,
    private val localDataSource: FootballLocalDataSource
) {
    var token: String = ""

    suspend fun getToken(): Flow<Result<TokenRaw>> {
        return remoteDataSource.fetchToken().apply {
            val result: Result<TokenRaw> = this.first()
            if (result.isSuccess) {
                val tokenRaw: TokenRaw = result.getOrNull()!!
                token = tokenRaw.token_type + " " + tokenRaw.access_token
            }
        }
    }

}