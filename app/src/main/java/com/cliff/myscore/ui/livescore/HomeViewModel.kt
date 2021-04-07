package com.cliff.myscore.ui.livescore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.bl.launchPeriodicAsync
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.FixtureLiveScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private var _liveScore: MutableLiveData<List<FixtureLiveScore>> = MutableLiveData()
    val liveScore: LiveData<List<FixtureLiveScore>>
        get() = _liveScore

    private lateinit var job: Deferred<Unit>

    fun getLiveScore() {
        job = viewModelScope.launchPeriodicAsync(TimeUnit.MINUTES.toMillis(5)) {
            runBlocking {
                repository.getLiveScores().collect {
                    _liveScore.postValue(it.getOrNull())

                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}