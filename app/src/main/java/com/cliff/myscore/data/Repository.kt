package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.Country
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: FootballRemoteDataSource,
    private val localDataSource: FootballLocalDataSource
) {

    suspend fun getCountries(): Flow<Result<List<Country>>> {
        return remoteDataSource.fetchCountries().map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!.response)
            else
                Result.failure(it.exceptionOrNull()!!)
        }
    }

}