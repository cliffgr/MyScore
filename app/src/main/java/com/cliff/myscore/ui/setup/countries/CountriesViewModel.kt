package com.cliff.myscore.ui.setup.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cliff.myscore.bl.handleErrors
import com.cliff.myscore.data.Repository
import com.cliff.myscore.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val countries: MutableLiveData<List<Country>> = MutableLiveData()

    private val _filteredCountries: MutableLiveData<List<Country>> = MutableLiveData()
    val filteredCountries: LiveData<List<Country>>
        get() = _filteredCountries


    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    fun getSupportedCountries() {
        _loader.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            repository.getCountries()
                .onEach {
                    countries.postValue(it.getOrNull()!!)
                    _filteredCountries.postValue(it.getOrNull()!!)
                    _loader.postValue(false)
                }
                .handleErrors {
                    _loader.postValue(false)
                }
                .collect()
        }
    }

    fun filteringCountries(query: String?) {
        query?.let {
            _filteredCountries.postValue(countries.value?.filter {
                it.name.contains(query, true)
            })
        }

    }
}