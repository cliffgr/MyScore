package com.cliff.myscore.data.local.sharePref

import android.content.Context
import android.content.SharedPreferences
import com.cliff.myscore.utils.Constants

class Pref (context: Context) {

    private var PREF_FIRST_RUN = "FIRST_RUN"

    public val preferences: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

    var intFirstRunPref: Boolean
        get() = preferences.getBoolean(PREF_FIRST_RUN, true)
        set(value) = preferences.edit().putBoolean(PREF_FIRST_RUN, value).apply()
}