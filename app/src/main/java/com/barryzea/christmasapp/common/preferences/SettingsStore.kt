package com.barryzea.christmasapp.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.barryzea.christmasapp.MyApp
import com.barryzea.christmasapp.data.model.PrefsEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 12/10/23.
 * Copyright (c)  All rights reserved.
 **/

const val SETTINGS_DATASTORE = "settings_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name= SETTINGS_DATASTORE)

class SettingsStore @Inject constructor(private val context:Context) {
 companion object{
   val NIGHT_MODE = booleanPreferencesKey("NIGHT_MODE")
   val DATE_NOTIFY = booleanPreferencesKey("DATE_NOTIFY")
   val ALREADY_IS_DATE = booleanPreferencesKey("ALREADY_IS_DATE")
 }
 suspend fun saveToDataStore(prefsEntity:PrefsEntity){
 context.dataStore.edit {
   it[NIGHT_MODE] = prefsEntity.darkTheme!!
   it[DATE_NOTIFY] = prefsEntity.dateNotify!!
   it[ALREADY_IS_DATE] = prefsEntity.alreadyIsDate!!
  }
 }
 fun getFromDataStore() = context.dataStore.data.map{
   PrefsEntity(
    darkTheme = it[NIGHT_MODE]?:false,
    dateNotify = it[DATE_NOTIFY]?:false,
       alreadyIsDate = it[ALREADY_IS_DATE]?:false)
 }
 suspend fun clearDataStore()=context.dataStore.edit{
  it.clear()
 }

}