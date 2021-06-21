package com.cliff.myscore.data.local.sharePref

import android.content.SharedPreferences
import javax.inject.Inject

class PrefManager @Inject constructor(private val preferences: SharedPreferences) {

    private var PREF_FIRST_RUN = "FIRST_RUN"

    var intFirstRunPref: Boolean
        get() = preferences.getBoolean(PREF_FIRST_RUN, true)
        set(value) = preferences.edit().putBoolean(PREF_FIRST_RUN, value).apply()
}