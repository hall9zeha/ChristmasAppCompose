package com.barryzea.christmasapp.ui.screens

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.localTheme
import com.barryzea.christmasapp.ui.theme.christmasTypography
import com.barryzea.christmasapp.ui.theme.textHintColorDark
import com.barryzea.christmasapp.ui.theme.textHintColorLight


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 22/10/23.
 * Copyright (c)  All rights reserved.
 **/

private lateinit var isBackPressed: MutableState<Boolean>
private lateinit var context:Context
@Composable
fun ReminderDetail(){
    var editTextValue by remember {mutableStateOf("")}
    //Este valor debe ser dinámico si vamos a sobreescribir el evento onBackPressed
    isBackPressed = remember { mutableStateOf(true) }

    context = LocalContext.current
    Scaffold (topBar = {topAppBar()}){
        Box(modifier = Modifier.padding(it)){
            Column {
                Row (
                    Modifier
                        .fillMaxSize()
                        .weight(0.9f)
                        ){
                    TextField(
                        modifier= Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(8.dp),
                        placeholder={Text("Escribe aquí tu recordatorio")},
                        colors= TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedPlaceholderColor = if(localTheme.current.isDark) textHintColorDark else textHintColorLight,
                            unfocusedPlaceholderColor = if(localTheme.current.isDark) textHintColorDark else textHintColorLight
                        ),
                        value =editTextValue,
                        onValueChange ={textValue->
                        editTextValue=textValue
                    }
                        )
                }
            }
            //Controlamos el evento onBackPressed
            BackHandler (enabled = isBackPressed.value, onBack = {onBack()})
        }
    }
}
private fun onBack(){
    Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show()
    isBackPressed.value=false

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(){
    TopAppBar(title = { Text(text = stringResource(R.string.reminder),
        fontSize=20.sp,
        style = christmasTypography.bodyLarge) },
        actions={
            IconButton(onClick = { }) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar),contentDescription = "")
            }
        })
}
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun preview(){
    ReminderDetail()
}