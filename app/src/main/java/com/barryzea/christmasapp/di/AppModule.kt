package com.barryzea.christmasapp.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.data.repository.MainRepository
import com.barryzea.christmasapp.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import javax.sql.DataSource


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  @Singleton
  @Provides
  fun contextProvides(app:Application):Context = app.applicationContext
}

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepoModule{
 @Binds
  abstract fun repositoryProvides(mainRepository: MainRepositoryImpl): MainRepository
}