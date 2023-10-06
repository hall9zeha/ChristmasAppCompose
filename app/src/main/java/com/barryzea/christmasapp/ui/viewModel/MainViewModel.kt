package com.barryzea.christmasapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
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

    private var _christmasCountdown:MutableLiveData<CountdownEntity> = MutableLiveData()
    lateinit var job:Job
    val christmasCountdown:LiveData<CountdownEntity> get() = _christmasCountdown

    fun fetchChristmasCountdown(){
        job=viewModelScope.launch {
            repository.getChristmasCountdown().cancellable()
                .catch { Log.e("ERROR", it.message.toString())}
                .collect{ _christmasCountdown.value=it}
        }
    }

}