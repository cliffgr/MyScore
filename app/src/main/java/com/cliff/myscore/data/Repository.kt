package com.cliff.myscore.data

import com.cliff.myscore.data.local.FootballLocalDataSource
import com.cliff.myscore.data.remote.FootballRemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: FootballRemoteDataSource,
    private val footballLocalDataSource: FootballLocalDataSource
) {

}