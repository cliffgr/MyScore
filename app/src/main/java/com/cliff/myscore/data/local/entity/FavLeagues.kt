package com.cliff.myscore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavLeague (@PrimaryKey val countryId: Int, flag : Boolean)
