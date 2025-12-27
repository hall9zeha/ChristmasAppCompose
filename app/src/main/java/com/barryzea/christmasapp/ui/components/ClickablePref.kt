package com.barryzea.christmasapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 13/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
@Composable
fun ClickablePref(
    @DrawableRes icon:Int,
    @StringRes iconDesc:Int,
    @StringRes name:Int,
    onClick:()->Unit
){
    Surface(
        color= Color.Transparent,
        modifier= Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        onClick = onClick
    ) {
        Column {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = icon), contentDescription = stringResource(
                        id = iconDesc
                    ),modifier=Modifier.size(24.dp) )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = name),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            //color = MaterialTheme.colorScheme.surfaceTint
                        ), modifier=Modifier.padding(16.dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis)
                }

            }
            Divider()
        }

    }

}