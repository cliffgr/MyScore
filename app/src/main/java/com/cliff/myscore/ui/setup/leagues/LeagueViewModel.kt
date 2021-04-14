package com.cliff.myscore.ui.setup.leagues

import android.util.Log
import androidx.lifecycle.*
import com.cliff.myscore.bl.handleErrors
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.Leagues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var leagues: MutableLiveData<List<Leagues>> = MutableLiveData()

    //Todo check this approach
    //https://stackoverflow.com/questions/61417190/unresolved-reference-aslivedata-while-converting-flow-to-livedata
    //https://developer.android.com/topic/libraries/architecture/coroutines#suspend

    fun leaguesByCountryCode(countryCode: String) {
        viewModelScope.launch {
            repository.getLeagues(countryCode).onEach {
                val listLeagues: List<Leagues> = it.getOrDefault(listOf())
                for (leagues in listLeagues) {
                    val response = repository.checkLeagueIfSelected(leagues.league.id)
                    if (response.isNotEmpty())
                        leagues.league.isSelected = response[0].flag
                }
            }.collect {
                leagues.postValue(it.getOrNull())
            }


            /*  val x : LiveData<FavLeague> = repository.checkLeagueIfSelected(item.league.id).asLiveData()
              Log.e("LeagueViewModel", "Leagues ${item.league.id} + ${x.value?.leagueId}  + ${x.value?.flag}")*/

            /*.collect { league ->
                    Log.e("LeagueViewModel", "Leagues ${item.league.id} + $league")
                }*/


        }
    }


    fun addFav(id: Int, flag: Boolean) {
        viewModelScope.launch {
            repository.addFavouriteLeague(id, flag)
        }
    }
}

