package com.cliff.myscore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cliff.myscore.data.local.entity.FavLeague
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLeaguesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteLeague(league: FavLeague)

    @Query("SELECT * FROM FavLeague")
    fun getAllFavouriteLeagues(): Flow<List<FavLeague>>
}