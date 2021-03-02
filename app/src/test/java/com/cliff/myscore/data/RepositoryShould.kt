package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.FixturesRaw
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class RepositoryShould {

    private val remoteDataSource = mock<FootballRemoteDataSource>()
    private val localDataSource = mock<FootballLocalDataSource>()
    private val countriesRaw = mock<CountriesRaw>()
    private val fixturesRaw = mock<FixturesRaw>()

    private val expectedFixtureFailure = Result.failure<FixturesRaw>(RuntimeException("error"))

    @Test
    fun fetchCountriesFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccess()
        repository.getCountries()
        verify(remoteDataSource, times(1)).fetchCountries()
    }

    @Test
    fun fetchLiveScoreFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccess()
        repository.getLiveScores()
        verify(remoteDataSource, times(1)).fetchLiveScores()
    }

    @Test
    fun fetchCorrectCountriesFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccess()
        assertEquals(
            countriesRaw.response, repository.getCountries().first().getOrNull()
        )
    }

    @Test
    fun fetchCorrectFixturesFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccess()
        assertEquals(
            fixturesRaw.response, repository.getLiveScores().first().getOrNull()
        )
    }

    @Test
    fun fetchFailureFixturesFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingFailure()
        assertEquals(
            expectedFixtureFailure, repository.getLiveScores().first().getOrNull()
        )
    }


    private fun fetchingSuccess(): Repository {
        runBlockingTest {
            whenever(remoteDataSource.fetchCountries()).thenReturn(
                flow {
                    emit(Result.success(countriesRaw))
                }
            )
            whenever(remoteDataSource.fetchLiveScores()).thenReturn(
                flow {
                    emit(Result.success(fixturesRaw))
                }
            )
        }
        return initializeRepository()
    }

    private fun fetchingFailure(): Repository {
        runBlockingTest {
            whenever(remoteDataSource.fetchLiveScores()).thenReturn(
                flow {
                    emit(expectedFixtureFailure)
                }
            )
        }
        return initializeRepository()
    }

    private fun initializeRepository(): Repository {
        return Repository(remoteDataSource, localDataSource)
    }
}