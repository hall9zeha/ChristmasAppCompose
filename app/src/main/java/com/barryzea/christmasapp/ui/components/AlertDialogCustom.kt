package com.barryzea.christmasapp.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.R


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 19/10/23.
 * Copyright (c)  All rights reserved.
 **/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  AlertDialogCustom(onDismissRequest:()->Unit,
                        onConfirmation:()->Unit)
{
 AlertDialog( onDismissRequest ={onDismissRequest()}

     ) {
     Card(
         modifier = Modifier
             .fillMaxWidth()
             .wrapContentHeight()
             .padding(16.dp),
         shape = RoundedCornerShape(16.dp),
     ) {
         Column {
             Column(
                 Modifier.fillMaxWidth(),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 Image(
                     painter = painterResource(id = R.drawable.christmas_bell),
                     contentDescription = "App icon",
                     contentScale = ContentScale.Fit,
                     modifier = Modifier
                         .height(120.dp)
                         .size(120.dp)
                 )
                 Text(
                     text = stringResource(R.string.aboutThisMsg).trimMargin(),
                     textAlign = TextAlign.Center
                 )
             }
             Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                 TextButton(
                     onClick = { onConfirmation() },
                     modifier = Modifier.padding(8.dp),
                 ) {
                     Text(stringResource(id = R.string.accept))
                 }
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
 AlertDialogCustom(
  {},{}
 )
}