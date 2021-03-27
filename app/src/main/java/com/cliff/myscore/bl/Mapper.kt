package com.cliff.myscore.bl

import android.util.Log
import android.view.View
import com.cliff.myscore.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.isActive


fun CountriesRaw.getResponse(): List<Country> {
    return response
}

fun FixturesRaw.getResponse(): List<FixtureLiveScore> {
    return response
}

fun LeaguesRaw.getResponse(): List<Leagues> {
    return response
}

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async {
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
        Log.e("Error", "Error : ${e}")
        ex(e.toString())
    }


fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}