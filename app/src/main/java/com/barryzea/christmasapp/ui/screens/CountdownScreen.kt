package com.barryzea.christmasapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 4/10/23.
 * Copyright (c)  All rights reserved.
 **/

private lateinit var fontFamily:FontFamily
private lateinit var viewModel:MainViewModel

@Composable
fun CountdownScreen(mainViewModel: MainViewModel = hiltViewModel()){
    viewModel =mainViewModel
    //val christmasFont=GoogleFont("Mountains of Christmas")//El nombre de la fuente que queremos descargar

    //La configuramos para ponerla al elemento Texto posteriormente
    /*fontFamily = FontFamily(Font(googleFont=christmasFont,
        fontProvider=getFontProviders(),
        weight = FontWeight.W700
    ))*/

    //ahora lo haremos usando archivos de fuente cargados al proyecto
    /*fontFamily = FontFamily(
        androidx.compose.ui.text.font.Font(
            R.font.mountains_of_christmas_bold,
            FontWeight.W700
        )
    )*/
    viewModel.fetchChristmasCountdown()
    val response by mainViewModel.christmasCountdown.observeAsState(CountdownEntity())
          Box {
              ChristmasBell()
              Column(
                  Modifier.fillMaxSize(),//.padding(it),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
              ) {
                  CountdownBody(response = response!!)
              }

    }
}@Composable
fun ChristmasBell(){
    Row (horizontalArrangement = Arrangement.SpaceBetween,modifier=Modifier.fillMaxWidth()){
        AnimationLottieView(animRes = LottieCompositionSpec.RawRes(R.raw.bells_anim_4),
            idLayout = "lottieViewMiniSanta" , 100.dp ,100.dp )
        AnimationLottieView(animRes = LottieCompositionSpec.RawRes(R.raw.lottie_anim_2),
            idLayout ="lottieViewGift",160.dp,160.dp )
    }
}
@Composable
fun CountdownBody(response: CountdownEntity, modifier: Modifier = Modifier
    .fillMaxWidth()) {
   if(!response.itsChristmas){
       ItsNotChristmasYet(response = response, modifier =modifier)
   }else{
        viewModel.job.cancel()
       ItsChristmas()  }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ItsChristmas(){
    Column(modifier= Modifier
        .layoutId("contentMainIsChristmas")
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment=Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ){
        Text(
            modifier=Modifier.layoutId("tvMerryChristmas"),
            text = stringResource(R.string.merryChristmas),
            fontSize = 46.sp,
            textAlign= TextAlign.Center,
            color = Color(0xff323232),
        )
        AnimationLottieView(animRes = LottieCompositionSpec.RawRes(R.raw.lottie_anim_1),
            idLayout = "lottieViewSanta",260.dp,260.dp )
        Text(
            modifier=Modifier.layoutId("tvEnjoyIt"),
            text = stringResource(R.string.enjoyIt),
            fontSize = 46.sp,
            textAlign= TextAlign.Center,
            color = Color(0xff323232),
        )
    }
}
@Composable
fun ItsNotChristmasYet(response:CountdownEntity, modifier:Modifier){
    Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Row(
            modifier = Modifier.layoutId("headerRow"),
            horizontalArrangement = Arrangement.Center
        ) {
            GenericTextView("tvStill",msgText = stringResource(R.string.still), Color(0xff323232),34.sp,modifier =modifier )

        }
        Box(
            modifier = Modifier
                .layoutId("contentMain")
                .height(260.dp)
                .width(260.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(id = R.drawable.christmas_wreath),
                contentDescription = "",
                modifier = Modifier
                    .layoutId("wreathImg")
                    .height(260.dp)
                    .width(260.dp)
            )
            Column(Modifier.padding(0.dp)) {
                GenericTextView("tvDays",msgText = response.day.toString(), Color(0xff323232),34.sp,modifier =modifier )
                GenericTextView("tvDaysDesc",msgText = stringResource(id = R.string.days), Color(0xff323232),16.sp,modifier =modifier )
            }
        }
        // Surface(shadowElevation = 2.dp) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .shadow(2.dp)
                // .height(80.dp)
                .wrapContentHeight()
                .background(color = Color(0xFFF05454))
        ) {

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                GenericTextView("tvHours",msgText = response.hour.toString(),Color.White,34.sp,modifier =modifier )
                GenericTextView("tvHoursDesc",msgText = stringResource(id = R.string.hour),Color.White,16.sp,modifier =modifier )
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                GenericTextView("tvMinutes",msgText = response.minute.toString(),Color.White,34.sp,modifier =modifier )
                GenericTextView("tvMinuteDesc",msgText = stringResource(id = R.string.minutes),Color.White,16.sp,modifier =modifier )
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                GenericTextView("tvSecond",msgText = response.second.toString(),Color.White,34.sp,modifier =modifier )
                GenericTextView("tvSecondDesc",msgText = stringResource(id = R.string.seconds),Color.White,16.sp,modifier =modifier )
           }
        }
        //}
    }
}
@Composable
fun AnimationLottieView(animRes:LottieCompositionSpec, idLayout: String, height: Dp,width:Dp ){
    val composition by rememberLottieComposition(spec = animRes)
    val progress by animateLottieCompositionAsState(composition = composition, iterations=LottieConstants.IterateForever)
    LottieAnimation(composition = composition,
        progress = { progress },
        modifier= Modifier
            .layoutId(idLayout)
            .height(height)
            .width(width))
}
@Composable
fun GenericTextView(idLayout:String, msgText:String, color: Color,size:TextUnit, modifier: Modifier){
    Text(
        text = msgText,
        modifier = modifier.layoutId(idLayout),
        color = color,
        fontSize = size,
        textAlign = TextAlign.Center
    )
   }

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun prev(){
    ItsNotChristmasYet(CountdownEntity(), Modifier)
}