package com.cliff.myscore.data.remote

import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.FixturesRaw
import com.cliff.myscore.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class FootballRemoteDataSourceShould : BaseUnitTest() {

    private val footballApi: FootballApi = mock()

    private val successCountryRaw = mock<CountriesRaw>()
    private val successFixturesRaw = mock<FixturesRaw>()

    private val expectedCountrySuccess = Result.success(successCountryRaw)
    private val expectedFixturesSuccess = Result.success(successFixturesRaw)

    private val exception = RuntimeException("Error")
    private val expectedCountryFailure = Result.failure<CountriesRaw>(exception)
    private val expectedFixturesFailure = Result.failure<FixturesRaw>(exception)

    @Test
    fun fetchApiCountriesFromFootballApi() = runBlockingTest {
        val footballRemoteDataSource = initDataSource()
        footballRemoteDataSource.fetchCountries().first()
        verify(footballApi, times(1)).fetchListCountries()
    }

    @Test
    fun fetchApiFixturesFromFootballApi() = runBlockingTest {
        val footballRemoteDataSource = initDataSource()
        footballRemoteDataSource.fetchLiveScores().first()
        verify(footballApi, times(1)).fetchLiveScoreFixtures()
    }

    @Test
    fun successCountriesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = success()
        assertEquals(expectedCountrySuccess, footballRemoteDataSource.fetchCountries().first())
    }

    @Test
    fun successFixturesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = success()
        assertEquals(expectedFixturesSuccess, footballRemoteDataSource.fetchLiveScores().first())
    }

    @Test
    fun failureCountriesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = failure()
        assertEquals(expectedCountryFailure, footballRemoteDataSource.fetchCountries().first())
    }

    @Test
    fun failureFixturesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = failure()
        assertEquals(expectedFixturesFailure, footballRemoteDataSource.fetchLiveScores().first())
    }

    private suspend fun success(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(footballApi.fetchListCountries()).thenReturn(successCountryRaw)
            whenever(footballApi.fetchLiveScoreFixtures()).thenReturn(successFixturesRaw)
        }
        return initDataSource()
    }

    private suspend fun failure(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(footballApi.fetchListCountries()).thenThrow(exception)
            whenever(footballApi.fetchLiveScoreFixtures()).thenThrow(exception)
        }
        return initDataSource()
    }

    private fun initDataSource(): FootballRemoteDataSource {
        return FootballRemoteDataSource(footballApi)
    }
}