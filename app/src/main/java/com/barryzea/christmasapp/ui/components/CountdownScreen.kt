package com.barryzea.christmasapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.barryzea.christmasapp.common.getDays
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 4/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun CountdownScreen(mainViewModel: MainViewModel = hiltViewModel()){
    mainViewModel.fetchChristmasCountdown()
    val response by mainViewModel.christmasCountdown.observeAsState("")
    Box (modifier= Modifier
        .fillMaxSize()
        .padding(8.dp),

    ){
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Greeting(response = response)
        }

    }
}
@Composable
fun Greeting(response: String, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier= Modifier.padding(8.dp),) {
        Text(
            text = response,
            modifier = modifier,
            textAlign = TextAlign.Center
        )
    }
}