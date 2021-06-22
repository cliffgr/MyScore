package com.cliff.myscore.ui.match

import androidx.lifecycle.*
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import com.cliff.myscore.model.MatchStatistics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    var events: MutableLiveData<Triple<List<FixtureLiveScore.Event>, Int, Int>> = MutableLiveData()

    private var _lineup: MutableLiveData<Pair<FixtureLiveScore.Lineup, FixtureLiveScore.Lineup>> =
        MutableLiveData()
    val lineup: LiveData<Pair<FixtureLiveScore.Lineup, FixtureLiveScore.Lineup>> = _lineup

    private val _statistics =
        MutableLiveData<Pair<FixtureLiveScore.Statistic, FixtureLiveScore.Statistic>>()
    val statistics: LiveData<List<MatchStatistics>> =
        Transformations.switchMap(_statistics) {
            transformStatistics(it)
        }

    private fun transformStatistics(input: Pair<FixtureLiveScore.Statistic, FixtureLiveScore.Statistic>): LiveData<List<MatchStatistics>> {
        val firstTeamStats = input.first.statistics
        val secondTeamStats = input.second.statistics
        val stats: MutableList<MatchStatistics> = mutableListOf()
        val liveData = MutableLiveData<List<MatchStatistics>>()
        for (i in firstTeamStats.indices) {
            val matchStat = MatchStatistics(
                if (firstTeamStats[i].value != null) firstTeamStats[i].value.toString()
                    .contains("%") else false,
                title = firstTeamStats[i].type,
                team1 = firstTeamStats[i].value ?: 0.0,
                team2 = secondTeamStats[i].value ?: 0.0
            )
            stats.add(matchStat)
        }
        liveData.value = stats.toList()
        return liveData
    }


    fun getDetailsOfMatch(fixtureId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getFixture(fixtureId).collect { result ->
                /**
                 * result.getOrDefault(mutableListOf()).let {
                 *    calculateData(it[0])
                 *  }
                 **/
                _fixtureMatch.postValue(result.getOrNull())
                calculateData(result.getOrDefault(mutableListOf())[0])
            }
        }
    }

    private fun calculateData(fixtureLiveScore: FixtureLiveScore) {
        teams.postValue(fixtureLiveScore.teams)
        fixture.postValue(fixtureLiveScore.fixture)
        league.postValue(fixtureLiveScore.league)
        score.postValue("${fixtureLiveScore.goals.home} - ${fixtureLiveScore.goals.away}")

        if (fixtureLiveScore.statistics.size == 2)
            _statistics.postValue(
                Pair(
                    fixtureLiveScore.statistics[0],
                    fixtureLiveScore.statistics[1]
                )
            )

        events.postValue(
            Triple(
                fixtureLiveScore.events,
                fixtureLiveScore.teams.home.id,
                fixtureLiveScore.teams.away.id
            )
        )

        if (fixtureLiveScore.lineups.size == 2)
            _lineup.postValue(Pair(fixtureLiveScore.lineups[0], fixtureLiveScore.lineups[1]))
    }
}