package com.cliff.myscore.bl

import android.content.res.Resources
import android.util.Log
import android.view.View
import com.cliff.myscore.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.lang.Math.floor


fun CountriesRaw.getResponse(): List<Country> {
    return response
}

fun FixturesRaw.getResponse(): List<FixtureLiveScore> {
    return response
}

fun LeaguesRaw.getResponse(): List<Leagues> {
    return response
}

fun StandingRaw.getResponse(): StandingLeague {
    return StandingLeague(this.response[0].league)
}

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async(Dispatchers.Default) {
    if (repeatMillis > 0) {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    } else {
        action()
    }
}

fun <T> Flow<T>.handleErrors(ex: (String) -> Unit): Flow<T> =
    catch { e ->
        Log.e("Error", "Error : $e")
        ex(e.toString())
    }


fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
