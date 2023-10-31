package com.barryzea.christmasapp.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.checkPermissions
import com.barryzea.christmasapp.data.model.PrefsEntity
import com.barryzea.christmasapp.ui.components.AlertDialogCustom
import com.barryzea.christmasapp.ui.components.ClickablePref
import com.barryzea.christmasapp.ui.components.SwitchCustomPref
import com.barryzea.christmasapp.ui.viewModel.SettingsViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 11/10/23.
 * Copyright (c)  All rights reserved.
 **/

private lateinit  var openAlertDialog:MutableState<Boolean>
private lateinit  var isClicked:MutableState<Boolean>
@Composable
fun SettingsScreen(viewModel: SettingsViewModel= hiltViewModel()){

    var stateSwitch = viewModel.isSwitchOn.collectAsState(false)
    var stateNotify = viewModel.notifyAllow.collectAsState(false)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ){
        if(it){
            isGranted(viewModel,stateNotify)
        }else{
            //todo crear un diálogo para explicar la necesidad de estos permisos
        }
    }

    openAlertDialog = remember { mutableStateOf(false) }
    isClicked=remember{ mutableStateOf(false) }
    if (isClicked.value) ShowDialog()

    Scaffold (modifier=Modifier.fillMaxSize()){
        Column(modifier= Modifier
            .padding(it)
            .padding(16.dp)
           ){
            Text(text = "Configuración", fontSize = 20.sp,modifier= Modifier.align(Alignment.CenterHorizontally))
           SwitchCustomPref(
               icon =R.drawable.ic_christmas_stars,
               iconDesc = R.string.nightMode,
               name =R.string.nightMode,
               state = stateSwitch
           ) {
              //guardamos la preferencia de modo noche
                   viewModel.toggleDarkModeSwitch(stateSwitch.value!!)
                   viewModel.saveToDataStore(PrefsEntity(
                       darkTheme = viewModel.isSwitchOn.value!!,
                       dateNotify = viewModel.notifyAllow.value))

           }
            SwitchCustomPref(
                icon =R.drawable.ic_christmas_bell ,
                iconDesc = R.string.christmasNotify ,
                name = R.string.christmasNotify ,
                state = stateNotify) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
                    checkPermissions(permission = Manifest.permission.POST_NOTIFICATIONS) { isGranted ->
                        if (isGranted) {
                            viewModel.toggleNotifySwitch(stateNotify.value)
                            viewModel.saveToDataStore(PrefsEntity(
                                darkTheme = viewModel.isSwitchOn.value!!,
                                dateNotify = viewModel.notifyAllow.value))
                        } else {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }else{
                    viewModel.toggleNotifySwitch(stateNotify.value)
                    viewModel.saveToDataStore(PrefsEntity(
                        darkTheme = viewModel.isSwitchOn.value!!,
                        dateNotify = viewModel.notifyAllow.value))
                }

            }
            ClickablePref(icon = R.drawable.ic_tree,
                iconDesc = R.string.aboutThis ,
                name = R.string.aboutThis) {

                isClicked.value = true
            }

        }

    }


}
@Composable
private fun ShowDialog(){
    AlertDialogCustom(onDismissRequest = { openAlertDialog.value =false; isClicked.value=false}, onConfirmation = {
        openAlertDialog.value = false
        isClicked.value=false
    })
}
fun isGranted(viewModel: SettingsViewModel, stateNotify: State<Boolean>) {
    viewModel.toggleNotifySwitch(stateNotify.value)
    viewModel.saveToDataStore(PrefsEntity(
        darkTheme = viewModel.isSwitchOn.value!!,
        dateNotify = viewModel.notifyAllow.value))
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsPreview(){
    SettingsScreen()
}