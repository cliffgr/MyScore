package com.cliff.myscore.ui.home

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
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val liveScore: MutableLiveData<List<FixtureLiveScore>> = MutableLiveData()

    fun getLiveScore() {
        viewModelScope.launch {
            repository.getLiveScores().collect {
                liveScore.postValue(it.getOrNull())
            }
        }
    }
}