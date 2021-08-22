package com.cliff.myscore.ui.standing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.StandingLeague
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StandingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var standing: MutableLiveData<StandingLeague> = MutableLiveData()

    fun standingByLeague(leagueCode: String, season: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getStanding(leagueCode, season).collect {

                val stL: StandingLeague = it.getOrThrow()
                if (stL.leagueStanding.standings.size > 1)
                    Log.e("Standing","More than one Leagues")

                    standing.postValue(it.getOrNull())
            }
        }
    }
}



