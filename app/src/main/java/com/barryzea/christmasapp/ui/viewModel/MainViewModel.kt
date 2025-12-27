package com.barryzea.christmasapp.ui.viewModel

import android.util.Log
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.common.SingleLiveData
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.data.model.PrefsEntity
import com.barryzea.christmasapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class MainViewModel @Inject constructor(private val repository:MainRepository, private val datastore:SettingsStore):ViewModel() {

    private var _christmasCountdown:SingleLiveData<CountdownEntity> = SingleLiveData()
    lateinit var job:Job

    private var _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    val christmasCountdown:SingleLiveData<CountdownEntity> get() = _christmasCountdown

    //Inicializamos el valor de loading en true para mostrar el splash screen
    init{
        viewModelScope.launch {
            //Le damos un retraso de 2 segundos para luego mostrar la pantalla principal
            delay(1000)
            _loading.value = false
        }
    }
    fun fetchChristmasCountdown(){
        job=viewModelScope.launch {
            repository.getChristmasCountdown().cancellable()
                .catch { Log.e("ERROR", it.message.toString())}
                .collect{ _christmasCountdown.value=it}
        }
    }


}