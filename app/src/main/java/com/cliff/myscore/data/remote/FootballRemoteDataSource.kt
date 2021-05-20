package com.cliff.myscore.data.remote


import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.FixturesRaw
import com.cliff.myscore.model.LeaguesRaw
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

    suspend fun fetchLiveScores(): Flow<Result<FixturesRaw>> {
        return flow {
            emit(Result.success(footballApi.fetchLiveScoreFixtures()))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
    }

    suspend fun fetchFixtureById(id: Int): Flow<Result<FixturesRaw>> {
        return flow {
            emit(Result.success(footballApi.fetchSelectedFixtures(id)))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
    }

    suspend fun fetchScheduledMatchesByDate(date : String) :Flow<Result<FixturesRaw>> {
        return flow {
            emit(Result.success(footballApi.fetchScheduledMatches(date)))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
    }


    suspend fun fetchLeagues(code: String, current: Boolean): Flow<Result<LeaguesRaw>> {
        return flow {
            emit(Result.success(footballApi.fetchLeagues(code, current)))
        }.catch { exception ->
            emit(Result.failure(exception))
        }
    }

}