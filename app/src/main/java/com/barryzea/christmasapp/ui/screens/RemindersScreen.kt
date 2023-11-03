package com.barryzea.christmasapp.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.cancelNotification
import com.barryzea.christmasapp.common.isScrolling
import com.barryzea.christmasapp.common.removeAlarm
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.ID_INSERTED_KEY
import com.barryzea.christmasapp.ui.components.RemindersList
import com.barryzea.christmasapp.ui.theme.blackSoft
import com.barryzea.christmasapp.ui.viewModel.ReminderViewModel

/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 21/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun RemindersScreen(
    onItemClick:(Long?)->Unit,
    navBackStackEntry: NavBackStackEntry,
    viewModel: ReminderViewModel = hiltViewModel(),
    scrollState:LazyStaggeredGridState = rememberLazyStaggeredGridState()
){

    val context = LocalContext.current
/*
* Ya que compose recreará las vistas componibles cada vez que haya cambios sutiles, como pasar un estado observable
* a través del constructor de esta pantalla, los cambios en este recrearán toda la pantalla y si tenemos
* llamadas a bases de datos o APIs, estas se harán más de una vez. Para evitar esas llamadas innecesarias de código
* que no es componible debemos ejecutar dicho código dentro de un contexto de corrutina como "LunchedEffect"
* que se ejecuta una sola vez  guardando su estado y no se vuelve a ejecutar a menos que cambie su llave.
* */

    val reminderList  by viewModel.reminderList.collectAsState(listOf())
    Log.e("TAG", "Fuera de corrutina")
    LaunchedEffect(key1 = true) {
       viewModel.getAllReminders()
        Log.e("TAG", "RemindersScreen")
        //Si tenemos el id de algún item enviado desde la pantalla detalle para hacer cambios
        if(navBackStackEntry?.savedStateHandle?.contains(ID_INSERTED_KEY) == true){
            val id=navBackStackEntry.savedStateHandle!!.get<Long>(ID_INSERTED_KEY)
            if(id!!>0){fetchUpdatedItem(id!!, viewModel)}
            else{scrollState.scrollToItem(0)}
            //removemos el valor guardado en el bundle para las siguientes recomposiciones
            navBackStackEntry.savedStateHandle.remove<Long>(ID_INSERTED_KEY)
        }
    }
    val reminder by viewModel.reminderById.observeAsState()
    reminder?.let{RefreshItemInList(reminder, viewModel) }
    Scaffold (floatingActionButton = {NewReminderFab(scrollState.isScrolling(), onItemClick)}){ paddingValues->
        RemindersList(reminderList, scrollState = scrollState,
            paddingValues,
            //Si abrimos un registro ya existente enviamos su id a la vista de detalle
            onItemClick = {reminder->onItemClick(reminder.id)},
            onDeleteClick = {reminder->deleteReminder(reminder,viewModel)})
    }
}
fun deleteReminder(reminder: Reminder, viewModel: ReminderViewModel){
    removeAlarm(reminder)
    cancelNotification(reminder.id)
    viewModel.deleteReminder(reminder)
}

@Composable
fun RefreshItemInList(reminder:Reminder?, viewModel: ReminderViewModel){
    LaunchedEffect(key1 = true ){
    reminder?.let{ viewModel.updateReminderList(it!!)}}
}

fun fetchUpdatedItem(idReminder:Long, viewModel: ReminderViewModel){
      viewModel.getReminderById(idReminder)
}
@Composable
fun NewReminderFab(isVisible: Boolean, onItemClick: (Long?) -> Unit){
    AnimatedVisibility(visible =isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),) {
        FloatingActionButton(
            //Si agregamos un nuevo registro el argumento enviado a la pantalla detalle será nulo
            onClick = {onItemClick(null) },

        ) {
            Icon(painter = painterResource(id = R.drawable.ic_christmas_bell), contentDescription ="add")
        }
    }
}



