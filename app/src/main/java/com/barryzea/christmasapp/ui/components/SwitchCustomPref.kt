package com.barryzea.christmasapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.localTheme
import com.barryzea.christmasapp.ui.theme.blackSoft
import com.barryzea.christmasapp.ui.theme.blackSoftForSurface
import com.barryzea.christmasapp.ui.theme.greenMoreSoft
import com.barryzea.christmasapp.ui.theme.greenSoft
import com.barryzea.christmasapp.ui.theme.salmonRed
import com.barryzea.christmasapp.ui.theme.salmonRedSoft
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 12/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
@Composable
fun SwitchCustomPref(
    @DrawableRes icon:Int,
    @StringRes iconDesc:Int,
    @StringRes name:Int,
    state: State<Boolean?>,
    onClick:()->Unit
){
    Surface(color= Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onClick = onClick) {
        Column {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = icon),
                        contentDescription = stringResource(id = iconDesc),
                        modifier=Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign= TextAlign.Start)
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = state.value!!, onCheckedChange ={onClick()},
                    colors = if(localTheme.current.isDark)SwitchDefaults.colors(
                            checkedThumbColor = salmonRedSoft,
                            checkedTrackColor= blackSoftForSurface,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                            uncheckedThumbColor = greenSoft
                        )else SwitchDefaults.colors(
                            //checkedThumbColor = greenSoft,
                            uncheckedThumbColor = greenSoft,
                            //checkedTrackColor = greenMoreSoft
                            )
                    )
            }
            Divider()
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun previewSwitch(){
    SwitchCustomPref(
        icon = R.drawable.ic_tree,
        iconDesc =R.string.app_name ,
        name =R.string.nightMode , state = MutableStateFlow(false).collectAsState()) {

    }
}