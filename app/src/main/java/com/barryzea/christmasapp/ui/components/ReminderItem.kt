package com.barryzea.christmasapp.ui.components

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.toDateString
import com.barryzea.christmasapp.data.model.Reminder


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 21/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
@Composable
fun ReminderItem(reminderEntity:Reminder, onClick:(Reminder)->Unit, onDeleteClick:(reminder:Reminder)->Unit){
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .clickable { onClick(reminderEntity) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = toDateString(reminderEntity.timeInMillis), modifier=Modifier.align(Alignment.End).padding(end = 4.dp, top = 4.dp))
        Text(text = reminderEntity.description, modifier= Modifier
            .wrapContentSize()
            .padding(8.dp) )
        Spacer(Modifier.size(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {onDeleteClick(reminderEntity)}) {
                Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription ="delete reminder icon")
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}
@Preview(showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewItem(){
    ReminderItem(reminderEntity = Reminder(id=0,description = "prueba de contenido de cardview item reminder"), onClick ={} , onDeleteClick = {})
}