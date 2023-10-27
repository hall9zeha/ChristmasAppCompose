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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.RemindersList
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 21/10/23.
 * Copyright (c)  All rights reserved.
 **/
private lateinit var goToDetail:MutableState<Boolean>

@Composable
fun RemindersScreen(
    onItemClick:(Long)->Unit,
    scrollState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    viewModel: MainViewModel = hiltViewModel()
){
    //FIXME corregir la recomposición contínua de esta pantalla al desplazarse en la lista de elementos, y al cargar una primera vez

    goToDetail = remember {mutableStateOf(false)}
    Log.e("TAG", "RemindersScreen" )
  /*  val idInserted by viewModel.idInserted.collectAsState()
    if(idInserted>0){
        Toast.makeText(LocalContext.current, "${idInserted}", Toast.LENGTH_SHORT).show()
    }
    //obtenemos el valor booleano al desplazarnos por la lista de elementos, actualizada en el ViewModelMain en MainActivity
   val scrollUpState  by viewModel.scrollUp.observeAsState()
*/
    val context = LocalContext.current
   if (goToDetail.value){
        //Ya que el argumento lo definimos como opcional podemos hacer uso de las dos formas
        //sin argumento
        //navController.navigate(Routes.ReminderDetail.route)
        //con argumento, lo necesitaremos al implementar la base de datos
       //navController.navigate(Routes.ReminderDetail.createRoute(25))
        goToDetail.value=false
    }
    //val scrollState = rememberLazyStaggeredGridState()
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
        RemindersList(getItems(), scrollState = scrollState, nestedScrollConnection,paddingValues)
    }
}
@Composable
fun NewReminderFab(isVisible: Boolean, onItemClick: (Long) -> Unit){
    AnimatedVisibility(visible =isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),) {
        FloatingActionButton(
            onClick = {onItemClick(25) },

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

