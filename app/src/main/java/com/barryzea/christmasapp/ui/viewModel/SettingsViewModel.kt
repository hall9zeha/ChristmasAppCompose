package com.barryzea.christmasapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.data.model.PrefsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 12/10/23.
 * Copyright (c)  All rights reserved.
 **/
@HiltViewModel
class SettingsViewModel @Inject constructor(private val datastore:SettingsStore):ViewModel() {

  private val _isSwitchOn:MutableStateFlow<Boolean> = MutableStateFlow(false)
  var isSwitchOn = _isSwitchOn.asStateFlow()

  private val _notifyAllow:MutableStateFlow<Boolean> = MutableStateFlow(false)
  var notifyAllow = _notifyAllow.asStateFlow()

  private var prefsEntity:PrefsEntity = PrefsEntity()

  init {
    getPreferences()
    toggleDarkModeSwitch(!prefsEntity.darkTheme!!)
    toggleNotifySwitch(!prefsEntity.dateNotify)
      }

  private fun getPreferences(){
    viewModelScope.launch {
      datastore.getFromDataStore().collect() {
        prefsEntity = it
        Log.e("TAG", it.dateNotify.toString() )
      }
    }
  }

  fun toggleDarkModeSwitch(stateSwitch:Boolean){
    _isSwitchOn.value = stateSwitch.not()

  }
  fun toggleNotifySwitch(stateAllow:Boolean){
    _notifyAllow.value = stateAllow.not()
  }
  fun saveToDataStore(prefsEntity:PrefsEntity){
    viewModelScope.launch {
      datastore.saveToDataStore(prefsEntity)
    }
  }
}