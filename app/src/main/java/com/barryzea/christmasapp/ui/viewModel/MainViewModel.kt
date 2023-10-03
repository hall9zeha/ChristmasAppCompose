package com.barryzea.christmasapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltViewModel
class MainViewModel @Inject constructor(private val repository:MainRepository):ViewModel() {

    private var _christmasCountdown:MutableLiveData<String> = MutableLiveData()
    val christmasCountdown:LiveData<String> get() = _christmasCountdown

    fun fetchChristmasCountdown(){
        viewModelScope.launch {
            repository.getChristmasCountdown()
                .catch { Log.e("ERROR", it.message.toString())}
                .collect{ _christmasCountdown.value=it}
        }
    }

}