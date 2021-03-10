package com.cliff.myscore.ui.setup.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val countries: MutableLiveData<List<Country>> = MutableLiveData()


    fun getSupportedCountries() {
        viewModelScope.launch {
            repository.getCountries().collect {
                countries.postValue(it.getOrNull()!!)
            }
        }
    }
}