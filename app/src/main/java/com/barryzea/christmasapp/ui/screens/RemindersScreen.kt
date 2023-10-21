package com.barryzea.christmasapp.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.components.ReminderItem


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 21/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
@Composable
fun RemindersScreen(scrollState: LazyStaggeredGridState){

    LazyVerticalStaggeredGrid(
        modifier=Modifier.fillMaxSize(),
        state=scrollState,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(2.dp),
       /* verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp)*/

    ){

        items(getItems(),key = {it.id}) {reminderItem->
            ReminderItem(reminderEntity = reminderItem, onClick ={} )
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