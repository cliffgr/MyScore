package com.cliff.myscore.data.local

import com.cliff.myscore.data.local.dao.FavouriteLeaguesDao
import com.cliff.myscore.data.local.entity.FavLeague
import javax.inject.Inject

class FootballLocalDataSource @Inject constructor(private val favouriteLeaguesDao: FavouriteLeaguesDao) {

    suspend fun addLeague(id: Int, flag: Boolean) {
        val favLeague = FavLeague(id, flag)
        favouriteLeaguesDao.insertFavouriteLeague(favLeague);
    }
}