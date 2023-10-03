package com.barryzea.christmasapp.di

import com.barryzea.christmasapp.data.repository.MainRepository
import com.barryzea.christmasapp.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

class AppModule {
}
@Module
@InstallIn(SingletonComponent::class)
abstract  class RepoModule{
 @Binds
  abstract fun repositoryProvides(mainRepository: MainRepositoryImpl): MainRepository
}