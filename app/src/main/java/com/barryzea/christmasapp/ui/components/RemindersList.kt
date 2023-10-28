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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.data.model.Reminder


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 25/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun RemindersList(
    itemList: List<Reminder>,
    scrollState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection,
    paddingValues: PaddingValues,
    onItemClick:(reminder:Reminder)->Unit,
    onDeleteClick:(reminder:Reminder)->Unit) {
    LazyVerticalStaggeredGrid(
    modifier = Modifier
    .fillMaxSize()
    .padding(paddingValues)
    .nestedScroll(nestedScrollConnection),
    state = scrollState,
    columns = StaggeredGridCells.Fixed(2),
    contentPadding = PaddingValues(2.dp)
    ) {
        items(itemList, key = { it.id }) { reminderItem ->
            ReminderItem(reminderEntity = reminderItem, onClick = {reminder->onItemClick(reminder)}, onDeleteClick={reminder->onDeleteClick(reminder)})
        }

    }
}
