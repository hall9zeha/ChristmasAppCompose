package com.barryzea.christmasapp.ui.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.REMINDER_ENTITY_KEY
import com.barryzea.christmasapp.common.getDatetimeWithoutHours
import com.barryzea.christmasapp.common.setAlarm
import com.barryzea.christmasapp.data.model.PrefsEntity
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.ClickablePref
import com.barryzea.christmasapp.ui.components.SwitchCustomPref
import com.barryzea.christmasapp.ui.viewModel.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 11/10/23.
 * Copyright (c)  All rights reserved.
 **/


@Composable
fun SettingsScreen(viewModel: SettingsViewModel= hiltViewModel(), scrollState: ScrollState){

    var stateSwitch = viewModel.isSwitchOn.collectAsState(false)
    var stateNotify = viewModel.notifyAllow.collectAsState(false)
    Scaffold (modifier=Modifier.fillMaxSize()){
        Column(modifier= Modifier
            .padding(it)
            .padding(16.dp)
            .verticalScroll(scrollState)){
            Text(text = "Configuración", fontSize = 20.sp,modifier= Modifier.align(Alignment.CenterHorizontally))
           SwitchCustomPref(
               icon =R.drawable.ic_christmas_stars,
               iconDesc = R.string.nightMode,
               name =R.string.nightMode,
               state = stateSwitch
           ) {
              //guardamos la preferencia de modo noche
               CoroutineScope(Dispatchers.IO).launch{
                   viewModel.toggleSwitch(stateSwitch.value!!)
                   viewModel.saveToDataStore(PrefsEntity(!stateSwitch.value!!))

               }
           }
            SwitchCustomPref(
                icon =R.drawable.ic_christmas_bell ,
                iconDesc = R.string.christmasNotify ,
                name = R.string.christmasNotify ,
                state = stateNotify) {
                viewModel.toggleNotifySwitch(stateNotify.value)
            }
            ClickablePref(icon = R.drawable.ic_tree,
                iconDesc = R.string.aboutThis ,
                name = R.string.aboutThis) {
                // Prueba sencilla de alarma con notificacion usando ala fecha actual
                val reminder = Reminder(25,"prueba de alarma", getDatetimeWithoutHours(Calendar.getInstance().timeInMillis))
                setAlarm(reminder)
            }

        }

    }

}
fun nightModeAllow(){

}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsPreview(){
    SettingsScreen(scrollState = rememberScrollState())
}