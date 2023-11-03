package com.barryzea.christmasapp.common

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Composable
fun LazyStaggeredGridState.isScrolling():Boolean{
  var previousIndex by remember(this){ mutableStateOf(firstVisibleItemIndex) }
  var previousScrollOfSet by remember(this){ mutableStateOf(firstVisibleItemScrollOffset) }
  return remember (this){
    derivedStateOf {
     if(previousIndex != firstVisibleItemIndex){
      previousIndex > firstVisibleItemIndex
     }else{
      previousScrollOfSet >=firstVisibleItemScrollOffset
     }.also {
      previousIndex = firstVisibleItemIndex
      //previousScrollOfSet = firstVisibleItemScrollOffset
     }
    }
  }.value
}