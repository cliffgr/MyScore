package com.cliff.myscore.bl

import com.cliff.myscore.model.CountriesRaw
import com.cliff.myscore.model.Country
import com.cliff.myscore.model.FixtureLiveScore
import com.cliff.myscore.model.FixturesRaw
import javax.inject.Inject


fun CountriesRaw.getResponse(): List<Country> {
    return response
}

fun FixturesRaw.getResponse(): List<FixtureLiveScore> {
    return response
}