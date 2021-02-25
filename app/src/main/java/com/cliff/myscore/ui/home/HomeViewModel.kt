package com.cliff.myscore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.myscore.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment with Hilt"
    }
    val text: LiveData<String> = _text
}