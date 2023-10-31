package com.barryzea.christmasapp.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 30/10/23.
 * Copyright (c)  All rights reserved.
 **/

class SingleLiveData<T> : MutableLiveData<T?>() {

 override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
  super.observe(owner, Observer { t ->
   if (t != null) {
    observer.onChanged(t)
    postValue(null)
   }
  })
 }
}