package com.cliff.myscore.di

import android.content.Context
import android.content.SharedPreferences

import com.cliff.myscore.data.local.sharePref.Pref

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): Pref {
        return  Pref(context)
    }
}