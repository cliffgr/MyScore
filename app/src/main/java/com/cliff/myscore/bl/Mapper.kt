package com.cliff.myscore.bl

import com.cliff.myscore.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import javax.inject.Inject


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