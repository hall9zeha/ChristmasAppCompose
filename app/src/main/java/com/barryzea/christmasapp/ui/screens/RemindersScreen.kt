package com.barryzea.christmasapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.Routes
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
   scrollState: LazyStaggeredGridState,
   navController: NavHostController,
   viewModel: MainViewModel
){
    //FIXME corregir la recomposición contínua de esta pantalla al desplazarse en la lista de elementos, y al cargar una primera vez

    goToDetail = remember {mutableStateOf(false)}
    Log.e("TAG", "RemindersScreen" )
    val idInserted by viewModel.idInserted.collectAsState()
    if(idInserted>0){
        Toast.makeText(LocalContext.current, "${idInserted}", Toast.LENGTH_SHORT).show()
    }
    //obtenemos el valor booleano al desplazarnos por la lista de elementos, actualizada en el ViewModelMain en MainActivity
   val scrollUpState  by viewModel.scrollUp.observeAsState()

    val context = LocalContext.current
   if (goToDetail.value){
        //Ya que el argumento lo definimos como opcional podemos hacer uso de las dos formas
        //sin argumento
        //navController.navigate(Routes.ReminderDetail.route)
        //con argumento, lo necesitaremos al implementar la base de datos
       navController.navigate(Routes.ReminderDetail.createRoute(25))
        goToDetail.value=false
    }
     Scaffold (floatingActionButton = {newReminderFab(!scrollUpState!!)}){paddingValues->
        RemindersList(scrollState = scrollState, paddingValues)
    }
}

@Composable
fun newReminderFab(isVisible:Boolean){
    AnimatedVisibility(visible =isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),) {
        FloatingActionButton(modifier = Modifier.offset(x = -10.dp,  y = -70.dp),
            onClick = { goToDetail.value=true},
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_tree), contentDescription ="add")
        }
    }
}

