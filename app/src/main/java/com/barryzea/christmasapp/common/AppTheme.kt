package com.barryzea.christmasapp.common


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 13/10/23.
 * Copyright (c)  All rights reserved.
 **/

enum class AppTheme{
 MODE_DAY,
 MODE_NIGHT,
 MODE_AUTO;
 companion object{
   fun fromOrdinal(ordinal: Int) = values()[ordinal]
 }
}