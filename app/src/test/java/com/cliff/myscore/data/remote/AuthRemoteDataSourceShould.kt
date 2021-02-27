package com.cliff.myscore.data.remote

import com.cliff.myscore.data.remote.api.AuthApi
import com.cliff.myscore.data.remote.api.FootballApi
import com.cliff.myscore.model.TokenRaw
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

class AuthRemoteDataSourceShould : BaseUnitTest() {

    private val authApi: AuthApi = mock()
    private val footballApi: FootballApi = mock()
    private val successTokenRaw = mock<TokenRaw>()
    private val expectedSuccess = Result.success(successTokenRaw)
    private val exception= RuntimeException("Error")
    private val expectedFailure = Result.failure<TokenRaw>(exception)

    @Test
    fun fetchApiTokenFromAuthApi() = runBlockingTest {
        val footballRemoteDataSource = initDataSource()
        footballRemoteDataSource.fetchToken().first()
        verify(authApi, times(1)).fetchToken("client_credentials")
    }

    @Test
    fun successTokenFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = success()
        assertEquals(expectedSuccess, footballRemoteDataSource.fetchToken().first())
    }

    @Test
    fun failureTokenFromApiResponse() = runBlockingTest {
        val footballRemoteDataSource = failure()
        assertEquals(expectedFailure, footballRemoteDataSource.fetchToken().first())
    }

    private suspend fun success(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(authApi.fetchToken("client_credentials")).thenReturn(successTokenRaw)
        }
        return initDataSource()

    }

    private suspend fun failure(): FootballRemoteDataSource {
        runBlockingTest {
            whenever(authApi.fetchToken("client_credentials")).thenThrow(exception)
        }
        return initDataSource()
    }

    private fun initDataSource(): FootballRemoteDataSource {
        return FootballRemoteDataSource(footballApi, authApi)
    }
}