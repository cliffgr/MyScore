package com.cliff.myscore.ui.leagues


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.Leagues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LeaguesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var leagues: MutableLiveData<List<Leagues>> = MutableLiveData()


    fun leaguesByCountryCode(countryCode: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.getLeagues(countryCode)
                .onEach {
                    val listLeagues: List<Leagues> = it.getOrDefault(listOf())
                    for (leagues in listLeagues) {
                        val response = repository.checkLeagueIfSelected(leagues.league.id)
                        if (response.isNotEmpty())
                            leagues.league.isSelected = response[0].flag
                    }
                }.collect {
                    leagues.postValue(it.getOrNull())
                }
        }
    }
}



