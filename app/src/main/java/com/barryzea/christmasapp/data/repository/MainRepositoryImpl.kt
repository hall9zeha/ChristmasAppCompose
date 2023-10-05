package com.barryzea.christmasapp.data.repository

import com.barryzea.christmasapp.common.getDays
import com.barryzea.christmasapp.data.model.CountdownEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

class MainRepositoryImpl @Inject constructor() :MainRepository{
    override suspend fun getChristmasCountdown(): Flow<CountdownEntity> {
        return flow{
            while(true){
                emit(getDays())
                delay(1000)
            }
        }.flowOn(Dispatchers.IO)
    }
}