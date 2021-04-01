package com.cliff.myscore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cliff.myscore.R
import com.cliff.myscore.ui.fixture.FixtureFragment

class FixtureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fixture_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FixtureFragment.newInstance())
                .commitNow()
        }
    }
}