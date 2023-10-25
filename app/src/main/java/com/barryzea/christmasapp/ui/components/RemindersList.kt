package com.barryzea.christmasapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.data.model.Reminder


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 25/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun RemindersList(scrollState: LazyStaggeredGridState, paddingValues: PaddingValues,) {
    LazyVerticalStaggeredGrid(
    modifier = Modifier
    .fillMaxSize()
    .padding(paddingValues),
    state = scrollState,
    columns = StaggeredGridCells.Fixed(2),
    contentPadding = PaddingValues(2.dp)
    ) {
        items(getItems(), key = { it.id }) { reminderItem ->
            ReminderItem(reminderEntity = reminderItem, onClick = {})
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