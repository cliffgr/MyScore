package com.cliff.myscore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cliff.myscore.data.local.dao.FavouriteLeaguesDao
import com.cliff.myscore.data.local.entity.FavLeague

@Database(entities = [FavLeague::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteLeaguesDao(): FavouriteLeaguesDao

}