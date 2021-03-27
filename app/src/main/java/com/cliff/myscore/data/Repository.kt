package com.cliff.myscore.data

import com.cliff.myscore.bl.getResponse
import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.local.entity.FavLeague
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.*
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ViewModelScoped
class Repository @Inject constructor(
    private val remoteDataSource: FootballRemoteDataSource,
    private val localDataSource: FootballLocalDataSource
) {
    //https://developer.android.com/codelabs/advanced-kotlin-coroutines#9
    suspend fun getCountries(): Flow<Result<List<Country>>> {
        return remoteDataSource.fetchCountries().map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!.getResponse())
            else
                Result.failure(it.exceptionOrNull()!!)
        }
    }

    suspend fun getLiveScores(): Flow<Result<List<FixtureLiveScore>>> {
        return remoteDataSource.fetchLiveScores().map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!.getResponse())
            else
                Result.failure(it.exceptionOrNull()!!)
        }
    }

    suspend fun getLeagues(countryCode: String): Flow<Result<List<Leagues>>> {
        return remoteDataSource.fetchLeagues(countryCode, true).map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!.getResponse())
            else
                Result.failure(it.exceptionOrNull()!!)
        }
    }

    suspend fun addFavouriteLeague(id: Int, flag: Boolean) {
        localDataSource.addLeague(id, flag)
    }

    suspend fun checkLeagueIfSelected(id: Int): List<FavLeague>{
        return localDataSource.checkLeague(id)
    }

}