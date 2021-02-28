package com.cliff.myscore.data.remote


import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.CountriesRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FootballRemoteDataSource @Inject constructor(
    private val footballApi: FootballApi
) {

    suspend fun fetchCountries(): Flow<Result<CountriesRaw>> {
        return flow {
            emit(Result.success(footballApi.fetchListCountries()))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
    }

}