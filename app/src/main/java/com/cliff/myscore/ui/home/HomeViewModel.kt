package com.cliff.myscore.ui.home

import android.util.Log
import android.widget.BaseExpandableListAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.bl.launchPeriodicAsync
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel()  {


    val liveScore: MutableLiveData<List<FixtureLiveScore>> = MutableLiveData()
    private lateinit var job: Deferred<Unit>;

    fun getLiveScore() {
        /*viewModelScope.launch {

        }*/
        job = viewModelScope.launchPeriodicAsync(TimeUnit.MINUTES.toMillis(1)) {
            runBlocking {
                repository.getLiveScores().collect {
                    liveScore.postValue(it.getOrNull())

                 /*   val map = it.getOrNull()!!.groupBy { result ->
                        result.league.name
                    }.toSortedMap()

                    Log.e("Home","Mapping")*/

                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}