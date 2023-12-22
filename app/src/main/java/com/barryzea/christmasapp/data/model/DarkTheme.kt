package com.barryzea.christmasapp.data.model

import androidx.compose.runtime.compositionLocalOf


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 13/10/23.
 * Copyright (c)  All rights reserved.
 **/

data class DarkTheme(val isDark:Boolean = false)
val localTheme = compositionLocalOf { DarkTheme()}
