package com.cliff.myscore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cliff.myscore.data.local.entity.FavLeague
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface FavouriteLeaguesDao {
    /**
     * Avoid suspend and flow
     *https://stackoverflow.com/questions/59170415/coroutine-flow-not-sure-how-to-convert-a-cursor-to-this-methods-return-type
     *
     * */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteLeague(league: FavLeague)

    @Query("SELECT * FROM FavLeague")
    fun getAllFavouriteLeagues(): Flow<List<FavLeague>>

    @Query("SELECT * FROM FavLeague WHERE leagueId = :leagueId")
    fun findLeagueById(leagueId: Int): Flow<FavLeague>

    @Query("SELECT * FROM FavLeague WHERE leagueId = :leagueId")
    suspend fun findSelectedLeagueById(leagueId: Int): List<FavLeague>


}