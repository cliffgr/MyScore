package com.cliff.myscore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavLeague (@PrimaryKey val countryId: Int,val flag : Boolean)
