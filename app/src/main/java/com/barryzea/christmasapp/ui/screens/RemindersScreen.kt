package com.barryzea.christmasapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.Routes
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.ReminderItem
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 21/10/23.
 * Copyright (c)  All rights reserved.
 **/
 private lateinit var isClicked:MutableState<Boolean>

@Composable
fun RemindersScreen(
    scrollState: LazyStaggeredGridState,
    navController: NavHostController,
    viewModel: MainViewModel
){
    isClicked = remember {mutableStateOf(false)}
    //obtenemos el valor booleano al desplazarnos por la lista de elementos, actualizada en el ViewModelMain en MainActivity
    val scrollUpState = viewModel.scrollUp.observeAsState()
    val context = LocalContext.current
    if (isClicked.value){
        navController.navigate(Routes.ReminderDetail.route
        )
        isClicked.value=false
    }

    Scaffold (floatingActionButton = {newReminderFab(!scrollUpState.value!!)}){paddingValues->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = scrollState,
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(2.dp),
            /* verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp)*/

        ) {

            items(getItems(), key = { it.id }) { reminderItem ->
                ReminderItem(reminderEntity = reminderItem, onClick = {})
            }

        }
    }


}

@Composable
fun newReminderFab(isVisible:Boolean){
    AnimatedVisibility(visible =isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),) {
        FloatingActionButton(modifier = Modifier.offset(x = -10.dp,  y = -70.dp),
            onClick = { isClicked.value=true},
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_tree), contentDescription ="add")
        }

    }
}

private fun getItems():List<Reminder>{
    val itemList= mutableListOf<Reminder>()
    for(i in 1 until 20){
        itemList.add(Reminder(i.toLong(),"contenido recordatorio de prueba N° $i"))
    }
    return itemList

}
