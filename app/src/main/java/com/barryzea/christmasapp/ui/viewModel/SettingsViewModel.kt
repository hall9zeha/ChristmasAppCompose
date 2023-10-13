package com.barryzea.christmasapp.ui.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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