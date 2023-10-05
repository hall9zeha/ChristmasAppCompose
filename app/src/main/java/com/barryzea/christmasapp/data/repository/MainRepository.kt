package com.barryzea.christmasapp.data.repository

import com.barryzea.christmasapp.data.model.CountdownEntity
import kotlinx.coroutines.flow.Flow


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

interface MainRepository {
    suspend fun  getChristmasCountdown():Flow<CountdownEntity>
}