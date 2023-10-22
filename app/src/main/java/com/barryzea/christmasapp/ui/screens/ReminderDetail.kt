package com.barryzea.christmasapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.R


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 22/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
@Composable
fun ReminderDetail(){
    var editTextValue by remember {mutableStateOf("Hello world")}
    Scaffold {
        Box(modifier = Modifier.padding(it)){
            Column {
                Row (
                    modifier= Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .weight(0.1f)
                        ,
                    horizontalArrangement = Arrangement.End){
                    Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "calendar")
                }
                Row (
                    Modifier
                        .fillMaxSize()
                        .weight(0.9f)
                        ){
                    TextField(
                        modifier= Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        value =editTextValue,
                        onValueChange ={textValue->
                        editTextValue=textValue
                    },
                        label={Text("Escribe aquí lo que quieras recordar")},
                        )
                }
            }
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun preview(){
    ReminderDetail()
}