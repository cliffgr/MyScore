package com.cliff.myscore.di

import android.content.Context
import androidx.room.Room
import com.cliff.myscore.data.local.AppDatabase
import com.cliff.myscore.data.local.dao.FavouriteLeaguesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesApplicationDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "Football.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavouriteLeaguesDao(appDatabase: AppDatabase): FavouriteLeaguesDao {
        return appDatabase.favouriteLeaguesDao()
    }
}
