package com.barryzea.christmasapp.common

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import com.barryzea.christmasapp.MyApp


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 15/10/23.
 * Copyright (c)  All rights reserved.
 **/


 fun checkPermissions(permission: String, isGranted: (Boolean) -> Unit){

    val context = MyApp.context
    //Android 13 requiere permisos para lanzar notificaciones
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        if(ContextCompat.checkSelfPermission(
                context,
                permission
        )==PackageManager.PERMISSION_GRANTED){
            //if granted
            isGranted(true)
        }else{
            isGranted(false)
            //launcher.launch(permission)
        }
    }
    else{
        isGranted(true)
    }
 }
