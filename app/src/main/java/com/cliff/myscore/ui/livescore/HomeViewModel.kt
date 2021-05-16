package com.cliff.myscore.ui.livescore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.bl.launchPeriodicAsync
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import com.cliff.myscore.model.LiveScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private var _liveScore: MutableLiveData<List<LiveScore>> = MutableLiveData()
    val liveScore: LiveData<List<LiveScore>>
        get() = _liveScore

    // private lateinit var job: Deferred<Unit>

    fun getLiveScore() {
        /* job = viewModelScope.launchPeriodicAsync(TimeUnit.MINUTES.toMillis(5)) {
             runBlocking {

             }
         }*/

        viewModelScope.launch {
            repository.getLiveScores().collect { result ->

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
                _liveScore.postValue(listOfMatches)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //job?.cancel()
    }
}