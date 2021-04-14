package com.cliff.myscore.ui.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private var _fixtureMatch: MutableLiveData<List<FixtureLiveScore>> = MutableLiveData()

    var teams: MutableLiveData<FixtureLiveScore.Teams> = MutableLiveData()
    var fixture: MutableLiveData<FixtureLiveScore.Fixture> = MutableLiveData()
    var league: MutableLiveData<FixtureLiveScore.League> = MutableLiveData()
    var score: MutableLiveData<String> = MutableLiveData()
    var events: MutableLiveData<List<FixtureLiveScore.Event>> = MutableLiveData()

    private var _lineup: MutableLiveData<Pair<FixtureLiveScore.Lineup, FixtureLiveScore.Lineup>> =
        MutableLiveData()
    val lineup: LiveData<Pair<FixtureLiveScore.Lineup, FixtureLiveScore.Lineup>> = _lineup

    private val _statistics =
        MutableLiveData<Pair<FixtureLiveScore.Statistic, FixtureLiveScore.Statistic>>()
    val statistics: LiveData<Pair<FixtureLiveScore.Statistic, FixtureLiveScore.Statistic>> =
        _statistics


    fun getDetailsOfMatch(fixtureId: Int) {
        viewModelScope.launch {
            repository.getFixture(fixtureId).collect { result ->
                /**
                 * result.getOrDefault(mutableListOf()).let {
                 *    calculateData(it[0])
                 *  }
                 **/
                _fixtureMatch.value = result.getOrNull()
                calculateData(result.getOrDefault(mutableListOf())[0])
            }
        }
    }

    private fun calculateData(fixtureLiveScore: FixtureLiveScore) {
        teams.value = fixtureLiveScore.teams
        fixture.value = fixtureLiveScore.fixture
        league.value = fixtureLiveScore.league
        score.value = "${fixtureLiveScore.goals.home} - ${fixtureLiveScore.goals.away}"

        if (fixtureLiveScore.statistics.size == 2)
            _statistics.value = Pair(fixtureLiveScore.statistics[0], fixtureLiveScore.statistics[1])

        events.value = fixtureLiveScore.events

        if (fixtureLiveScore.lineups.size == 2)
            _lineup.value = Pair(fixtureLiveScore.lineups[0], fixtureLiveScore.lineups[1])
    }
}