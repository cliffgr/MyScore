package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.CountriesRaw
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: FootballRemoteDataSource,
    private val localDataSource: FootballLocalDataSource
) {

    suspend fun getCountries(): Flow<Result<CountriesRaw>> {
        return remoteDataSource.fetchCountries()
    }

}