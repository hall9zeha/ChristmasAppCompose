package com.barryzea.christmasapp.ui.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.common.SettingsStore
import com.barryzea.christmasapp.data.model.PrefsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 12/10/23.
 * Copyright (c)  All rights reserved.
 **/
@HiltViewModel
class SettingsViewModel @Inject constructor():ViewModel() {

  private val _isSwitchOn:MutableStateFlow<Boolean> = MutableStateFlow(false)
  var isSwitchOn = _isSwitchOn.asStateFlow()

  fun toggleSwitch(stateSwitch:Boolean){
    _isSwitchOn.value = stateSwitch.not()
  }

}