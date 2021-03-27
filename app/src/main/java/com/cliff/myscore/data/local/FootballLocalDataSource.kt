package com.cliff.myscore.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.cliff.myscore.data.local.dao.FavouriteLeaguesDao
import com.cliff.myscore.data.local.entity.FavLeague
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FootballLocalDataSource @Inject constructor(private val favouriteLeaguesDao: FavouriteLeaguesDao) {

    suspend fun addLeague(id: Int, flag: Boolean) {
        val favLeague = FavLeague(id, flag)
        favouriteLeaguesDao.insertFavouriteLeague(favLeague);
    }

    suspend fun checkLeague(id: Int): List<FavLeague> {
        return favouriteLeaguesDao.findSelectedLeagueById(id)
    }
}