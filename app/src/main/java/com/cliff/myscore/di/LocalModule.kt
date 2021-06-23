package com.cliff.myscore.di

import android.content.Context
import android.content.SharedPreferences

import com.cliff.myscore.data.local.sharePref.PrefManager
import com.cliff.myscore.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideSessionManager(preferences: SharedPreferences): PrefManager {
        return PrefManager(preferences)
    }
}