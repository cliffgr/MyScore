package com.cliff.myscore.data.remote

import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.CountriesRaw
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
    private val successTokenRaw = mock<CountriesRaw>()
    private val expectedSuccess = Result.success(successTokenRaw)
    private val exception= RuntimeException("Error")
    private val expectedFailure = Result.failure<CountriesRaw>(exception)

    @Test
    fun fetchApiCountriesFromAuthApi() = runBlockingTest {
        val footballRemoteDataSource = initDataSource()
        footballRemoteDataSource.fetchCountries().first()
        verify(footballApi, times(1)).fetchListCountries()
    }

    @Test
    fun successCountriesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = success()
        assertEquals(expectedSuccess, footballRemoteDataSource.fetchCountries().first())
    }

    @Test
    fun failureCountriesFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = failure()
        assertEquals(expectedFailure, footballRemoteDataSource.fetchCountries().first())
    }

    private suspend fun success(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(footballApi.fetchListCountries()).thenReturn(successTokenRaw)
        }
        return initDataSource()

    }

    private suspend fun failure(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(footballApi.fetchListCountries()).thenThrow(exception)
        }
        return initDataSource()
    }

    private fun initDataSource(): FootballRemoteDataSource {
        return FootballRemoteDataSource(footballApi)
    }
}