package com.barryzea.christmasapp.common

import androidx.compose.ui.text.googlefonts.GoogleFont
import com.barryzea.christmasapp.R


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 6/10/23.
 * Copyright (c)  All rights reserved.
 **/

fun getFontProviders():GoogleFont.Provider{
    return GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}
