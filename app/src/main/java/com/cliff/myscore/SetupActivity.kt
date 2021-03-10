package com.cliff.myscore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cliff.myscore.ui.favourite.FavouriteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

    }
}