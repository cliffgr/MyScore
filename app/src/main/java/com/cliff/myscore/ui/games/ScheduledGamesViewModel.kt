package com.cliff.myscore.ui.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import com.cliff.myscore.model.LiveScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledGamesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private var _scheduledMatches: MutableLiveData<List<LiveScore>> = MutableLiveData()
    val scheduledMatches: LiveData<List<LiveScore>>
        get() = _scheduledMatches


    fun getScheduledMatchesForTheDay(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getScheduledMatches(date).collect { result ->
                val listOfMatches: MutableList<LiveScore> = arrayListOf()
                val fixturesList: List<FixtureLiveScore> = result.getOrNull()!!
                fixturesList
                    .distinctBy { it.league.country }
                    .map { it.league.country }
                    .forEach { country ->
                        val liveScore = LiveScore(country, null, null, true)
                        listOfMatches.add(liveScore)
                        fixturesList.filter { country == it.league.country }.forEach {
                            val internalScore = LiveScore(null, null, it, false)
                            liveScore.countryLogo = it.league.flag
                            listOfMatches.add(internalScore)
                        }
                    }
                _scheduledMatches.postValue(listOfMatches)
            }
        }
    }
}