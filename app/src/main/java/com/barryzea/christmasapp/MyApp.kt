package com.barryzea.christmasapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltAndroidApp
class MyApp:Application(){
    companion object{
        private var _context: Context?=null
        val context  get() = _context!!
    }
    override fun onCreate() {
        super.onCreate()
        if(_context==null){
            _context = this
        }
    }
}