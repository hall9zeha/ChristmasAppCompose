package com.barryzea.christmasapp.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.ID_INSERTED_KEY
import com.barryzea.christmasapp.ui.components.RemindersList
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
    scrollState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    viewModel: ReminderViewModel = hiltViewModel()
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

    LaunchedEffect(key1 = true) {
        viewModel.getAllReminders()
        Log.e("TAG", "RemindersScreen")
        if(navBackStackEntry?.savedStateHandle?.contains(ID_INSERTED_KEY) == true){
            val id=navBackStackEntry.savedStateHandle!!.get<Long>(ID_INSERTED_KEY)
            Toast.makeText( context,id.toString(), Toast.LENGTH_SHORT).show()
            navBackStackEntry.savedStateHandle.remove<Long>(ID_INSERTED_KEY)
        }
    }

    val fabVisibility= rememberSaveable{
        mutableStateOf(true)
    }
     val nestedScrollConnection = remember{
        object : NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if(available.y < -1)fabVisibility.value = false
                if(available.y >1)fabVisibility.value = true
                return Offset.Zero
            }
        }
    }
     Scaffold (floatingActionButton = {NewReminderFab(fabVisibility.value, onItemClick)}){ paddingValues->
        RemindersList(reminderList, scrollState = scrollState,
            nestedScrollConnection,
            paddingValues,
            onItemClick = {reminder->onItemClick(reminder.id)},
            onDeleteClick = {reminder->deleteReminder(reminder,viewModel)})
    }
}
fun deleteReminder(reminder: Reminder, viewModel: ReminderViewModel){
    viewModel.deleteReminder(reminder)

  }
@Composable
fun NewReminderFab(isVisible: Boolean, onItemClick: (Long?) -> Unit){
    AnimatedVisibility(visible =isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),) {
        FloatingActionButton(
            onClick = {onItemClick(null) },

        ) {
            Icon(painter = painterResource(id = R.drawable.ic_tree), contentDescription ="add")
        }
    }
}
private fun getItems():List<Reminder>{
    val itemList= mutableListOf<Reminder>()
    for(i in 1 until 20){
        itemList.add(Reminder(i.toLong(),"contenido recordatorio de prueba N° $i".repeat(i)))
    }
    return itemList

}
