package com.cliff.myscore.ui.setup.leagues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.Leagues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var leagues: MutableLiveData<List<Leagues>> = MutableLiveData()


    fun leaguesByCountryCode(countryCode: String) {
        viewModelScope.launch {
            repository.getLeagues(countryCode).collect {
                leagues.postValue(it.getOrNull())
            }
        }
    }

    fun addFav(id: Int, flag: Boolean) {
        viewModelScope.launch {
            repository.addFavouriteLeague(id, flag)
        }
    }
}