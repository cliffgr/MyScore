package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import com.cliff.myscore.model.TokenRaw
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RepositoryShould {

    private val remoteDataSource = mock<FootballRemoteDataSource>()
    private val localDataSource = mock<FootballLocalDataSource>()

    private val tokenRaw: TokenRaw = TokenRaw(
        "TokenString",
        3600,
        "Bearer"
    )

    private val expectedToken: String = "Bearer TokenString"

    @Test
    fun fetchTokenFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccessToken()
        repository.getToken()
        verify(remoteDataSource, times(1)).fetchToken()
    }

    @Test
    fun fetchCorrectTokenFromRemoteDataSource() = runBlockingTest {
        val repository = fetchingSuccessToken()
        assertEquals(
            tokenRaw.access_token,
            repository.getToken().first().getOrNull()!!.access_token
        )
    }

    @Test
     fun fetchTokenAndSaveInRepo() = runBlockingTest {
         val repository = fetchingSuccessToken()
         repository.getToken()
         assertEquals(expectedToken, repository.token)

     }


    private fun fetchingSuccessToken(): Repository {
        runBlockingTest {
            whenever(remoteDataSource.fetchToken()).thenReturn(
                flow {
                    emit(Result.success(tokenRaw))
                }
            )
        }
        return initializeRepository()
    }

    private fun initializeRepository(): Repository {
        return Repository(remoteDataSource, localDataSource)
    }
}