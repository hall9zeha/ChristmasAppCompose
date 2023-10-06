package com.barryzea.christmasapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.getFontProviders
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 4/10/23.
 * Copyright (c)  All rights reserved.
 **/

private lateinit var fontFamily:FontFamily
@Composable
fun CountdownScreen(mainViewModel: MainViewModel = hiltViewModel()){

    //val christmasFont=GoogleFont("Mountains of Christmas")//El nombre de la fuente que queremos descargar

    //La configuramos para ponerla al elemento Texto posteriormente
    /*fontFamily = FontFamily(Font(googleFont=christmasFont,
        fontProvider=getFontProviders(),
        weight = FontWeight.W700
    ))*/

    //ahora lo haremos usando archivos de fuente cargados al proyecto
    fontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(
            R.font.mountains_of_christmas_bold,
            FontWeight.W700
        )
    )
    mainViewModel.fetchChristmasCountdown()
    val response by mainViewModel.christmasCountdown.observeAsState(CountdownEntity())

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        elevation =CardDefaults.cardElevation( defaultElevation = 4.dp),
        shape= RoundedCornerShape(16.dp)
    ) {
        Box() {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                countdownBody(response = response!!)

            }

        }
    }
}

@Composable
fun countdownBody(response: CountdownEntity, modifier: Modifier = Modifier
    .fillMaxWidth()) {
   if(!response.itsChristmas){
       itsNotChristmasYet(response = response, modifier =modifier)
   }
}

@Composable
fun itsNotChristmasYet(response:CountdownEntity, modifier:Modifier){
    Box(modifier = Modifier
        .layoutId("contentMain")
        .height(260.dp)
        .width(260.dp),
        contentAlignment =  Alignment.Center){
        Image( painterResource(id = R.drawable.christmas_wreath),
            contentDescription="",
            modifier= Modifier
                .layoutId("wreathImg")
                .height(260.dp)
                .width(260.dp)
        )
        Column(Modifier.padding(0.dp)) {
            Text(
                text = response.day.toString(),
                modifier = modifier.layoutId("tvDays"),
                color = Color(0xff323232),
                fontSize = 34.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.days),
                modifier = modifier
                    .layoutId("tvDaysDesc")
                    .padding(0.dp, 0.dp, 0.dp, 8.dp),
                color = Color(0xff323232),
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .height(80.dp)

            .background(color = Color(0xFFF05454)),) {

        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.hour.toString(),
                modifier = modifier.layoutId("tvHours"),
                color= Color(0xffffffff),
                fontSize=34.sp,
                fontFamily=fontFamily,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.hour),
                modifier=Modifier.layoutId("tvHoursDesc"),
                fontFamily=fontFamily,
                color= Color(0xffffffff),
            )
        }
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.minute.toString(),
                modifier = modifier.layoutId("tvMinutes"),
                color= Color(0xffffffff),
                fontSize=34.sp,
                fontFamily=fontFamily,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.minutes),
                modifier=Modifier.layoutId("tvMinutesDesc"),
                fontFamily=fontFamily,
                color= Color(0xffffffff),
            )
        }
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.second.toString(),
                modifier = modifier.layoutId("tvSeconds"),
                color= Color(0xffffffff),
                fontSize=34.sp,
                fontFamily=fontFamily,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.seconds),
                modifier=Modifier.layoutId("tvSecondsDesc"),
                fontFamily=fontFamily,
                color= Color(0xffffffff),
            )
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun prev(){
    countdownBody(response = CountdownEntity())
}