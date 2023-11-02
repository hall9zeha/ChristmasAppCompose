package com.barryzea.christmasapp.ui.screens

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.cancelNotification
import com.barryzea.christmasapp.common.setAlarm
import com.barryzea.christmasapp.common.toDateString
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.data.model.localTheme
import com.barryzea.christmasapp.ui.theme.christmasTypography
import com.barryzea.christmasapp.ui.theme.textHintColorDark
import com.barryzea.christmasapp.ui.theme.textHintColorLight
import com.barryzea.christmasapp.ui.viewModel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 22/10/23.
 * Copyright (c)  All rights reserved.
 **/

private lateinit var showDialog:MutableState<Boolean>
private lateinit var context:Context
private lateinit var editTextValue:MutableState<String>
private lateinit  var timeInMillisForSave:MutableState<Long>
private lateinit var reminderEnable:MutableState<Boolean>


@OptIn(ExperimentalMaterial3Api::class)
private lateinit var datePickerState:DatePickerState
private lateinit var isNewRegister:MutableState<Boolean>


@Composable
fun ReminderDetail(viewModel:ReminderViewModel = hiltViewModel(), idReminder: Long?, upPress:(Long?)->Unit) {
    //Preferencias


    //Propiedades para guardar
    editTextValue = remember {mutableStateOf("")}
    timeInMillisForSave = remember{ mutableLongStateOf(Calendar.getInstance().timeInMillis) }
    reminderEnable = rememberSaveable{ mutableStateOf(false) }
    //*********************************************************************
    showDialog = rememberSaveable{ mutableStateOf(false) }
    context = LocalContext.current
    isNewRegister = remember{ mutableStateOf(false) }

    val reminderSavedId by viewModel.idInserted.observeAsState(0)
    val reminderUpdatedRow by viewModel.updatedRow.observeAsState(0)
    val reminderById by viewModel.reminderById.observeAsState(Reminder())
    if(reminderById?.id!!>0) SetUpReminderById(reminderById)

    //Si se guardó un nuevo registro
    if(reminderSavedId > 0) {
        // Si las notificaciones están activadas
        if(viewModel.prefsEntity.dateNotify){
            setAlarm(Reminder(reminderSavedId,
                editTextValue.value,
                timeInMillisForSave.value,
                reminderEnable.value))
        }
        upPress(0)}
    //Si se modificó un registro existente
    if(reminderUpdatedRow>0) {
        // Si las notificaciones están activadas
        if(viewModel.prefsEntity.dateNotify){
             setAlarm(Reminder(idReminder!!,
                editTextValue.value,
                timeInMillisForSave.value,
                reminderEnable.value))
        }
        upPress(idReminder)}

    /*
    * Capturamos el argumento
    * */
    getArguments(idReminder, viewModel)

    Scaffold (topBar = { TopAppBar() }){
        Box(modifier = Modifier.padding(it)){
            Column {
                Row(Modifier.align(Alignment.End)){
                    Text(text = toDateString(timeInMillisForSave.value) )
                }
                Row (
                    Modifier
                        .fillMaxSize()
                        .weight(0.8f)
                        ){
                    TextField(
                        modifier= Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(8.dp),
                        placeholder={Text(stringResource(R.string.writeYourReminderHere))},
                        colors= TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedPlaceholderColor = if(localTheme.current.isDark) textHintColorDark else textHintColorLight,
                            unfocusedPlaceholderColor = if(localTheme.current.isDark) textHintColorDark else textHintColorLight
                        ),
                        value =editTextValue.value,
                        onValueChange ={textValue->
                        editTextValue.value=textValue
                    })
                }
            }
            //Controlamos el evento onBackPressed
            BackHandler (onBack = {onBack(viewModel, idReminder)})
            //si se hizo click en el ícono calendario del topBar
            if(showDialog.value) ShowDatePickerDialog()
        }
    }
}
private fun getArguments(idReminder: Long?, viewModel: ReminderViewModel){
    idReminder?.let{id->
        if(id>0){
            isNewRegister.value=false
            viewModel.getReminderById(idReminder)
          }else{
            isNewRegister.value=true
          }
    }

}

@Composable
private fun SetUpReminderById(reminder: Reminder?){
reminder.let {
    editTextValue.value = reminder!!.description
    timeInMillisForSave.value = reminder!!.timeInMillis
    reminderEnable.value = reminder!!.enable
}
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
    TopAppBar(title = { Text(text = stringResource(R.string.reminder),
        fontSize=20.sp,
        style = christmasTypography.bodyLarge) },
        actions={
            IconButton(onClick = {
                showDialog.value=true
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_winter_cal),contentDescription = "")
            }
        })
}

private fun saveOrUpdate(viewModel: ReminderViewModel, idReminder: Long?) {
    if(isNewRegister.value){
        val reminder = Reminder(description= editTextValue.value, timeInMillis = timeInMillisForSave?.value!!, enable = reminderEnable.value)
        viewModel.saveReminder(reminder)
    }else{
        val reminder = Reminder(id = idReminder!!, description= editTextValue.value, timeInMillis = timeInMillisForSave?.value!!, enable = reminderEnable.value)
        viewModel.updateReminder(reminder)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePickerDialog(){
    datePickerState = rememberDatePickerState(initialSelectedDateMillis = Calendar.getInstance().timeInMillis)
    DatePickerDialog(onDismissRequest = { showDialog.value=false }, confirmButton = {
        TextButton(onClick = {
            showDialog.value = false
            timeInMillisForSave.value= datePickerState.selectedDateMillis!!
            reminderEnable.value=true
        }) {
            Text("Ok")
        }
    },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(stringResource(id = R.string.cancel))
            }
        }){
        DatePicker(state = datePickerState)
    }

}
private fun onBack(viewModel: ReminderViewModel, idReminder: Long?) {
    saveOrUpdate(viewModel, idReminder)
}
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun Preview(){
   /* ReminderDetail(0

    )*/
}