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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 4/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun CountdownScreen(mainViewModel: MainViewModel = hiltViewModel()){
    mainViewModel.fetchChristmasCountdown()
    val response by mainViewModel.christmasCountdown.observeAsState(CountdownEntity())
    Box (modifier= Modifier
        .fillMaxSize()
        .padding(8.dp),

    ){
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Greeting(response = response!!)

        }

    }
}


@Composable
fun Greeting(response: CountdownEntity, modifier: Modifier = Modifier
    .fillMaxWidth()) {
    Box(modifier = Modifier
        .height(220.dp)
        .width(220.dp),
        contentAlignment =  Alignment.Center){
        Image( painterResource(id = R.drawable.christmas_wreath),
            contentDescription="",
            modifier= Modifier
                .height(220.dp)
                .width(220.dp)
           )
        Text(
            text = response.day.toString(),

            modifier = modifier,
            color= Color(0xff323232),
            fontSize=34.sp,
            textAlign = TextAlign.Center
        )
    }
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier
            .padding(4.dp)
            .height(80.dp)
            .background(color = Color(0xFFF05454)),) {

        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.hour.toString(),
                modifier = modifier,
                color= Color(0xffffffff),
                fontSize=34.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.hour),
                color= Color(0xffffffff),
            )
        }
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.minute.toString(),
                modifier = modifier,
                color= Color(0xffffffff),
                fontSize=34.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.minutes),
                color= Color(0xffffffff),
            )
        }
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = response.second.toString(),
                modifier = modifier,
                color= Color(0xffffffff),
                fontSize=34.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text= stringResource(R.string.seconds),
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
    Greeting(response = CountdownEntity())
}