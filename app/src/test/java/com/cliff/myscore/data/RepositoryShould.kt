package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.CountriesRaw
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RepositoryShould {

    private val remoteDataSource = mock<FootballRemoteDataSource>()
    private val localDataSource = mock<FootballLocalDataSource>()
    private val countriesRaw = mock<CountriesRaw>()

    @Test
    fun fetchCountriesFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccessToken()
        repository.getCountries()
        verify(remoteDataSource, times(1)).fetchCountries()
    }

    @Test
    fun fetchLiveScoreFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccessToken()
        repository.getLiveScores()
        verify(remoteDataSource, times(1)).fetchLiveScores()
    }

    @Test
    fun fetchCorrectTokenFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccessToken()
        assertEquals(
            countriesRaw.response, repository.getCountries().first().getOrNull()
        )
    }


    private fun fetchingSuccessToken(): Repository {
        runBlockingTest {
            whenever(remoteDataSource.fetchCountries()).thenReturn(
                flow {
                    emit(Result.success(countriesRaw))
                }
            )
        }
        return initializeRepository()
    }

    private fun initializeRepository(): Repository {
        return Repository(remoteDataSource, localDataSource)
    }
}