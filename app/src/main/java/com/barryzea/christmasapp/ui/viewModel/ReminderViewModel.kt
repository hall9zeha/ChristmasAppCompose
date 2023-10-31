package com.barryzea.christmasapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barryzea.christmasapp.common.SingleLiveData
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 28/10/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltViewModel
class ReminderViewModel @Inject constructor(private val repository: MainRepository):ViewModel() {

 private val viewModelJob= SupervisorJob()
 private val uiScope= CoroutineScope(Dispatchers.Main  + viewModelJob)

 private var _remindersList:MutableStateFlow<MutableList<Reminder>> = MutableStateFlow(mutableListOf())
 val reminderList:StateFlow<MutableList<Reminder>> = _remindersList

 private var _reminderById:SingleLiveData<Reminder> = SingleLiveData()
 val reminderById:SingleLiveData<Reminder> = _reminderById

 private var _idInserted:MutableLiveData<Long> = MutableLiveData()
 val idInserted:LiveData<Long> = _idInserted

 private var _updatedRow:MutableLiveData<Int> = MutableLiveData()
 val updatedRow:LiveData<Int> = _updatedRow

 override fun onCleared() {
  super.onCleared()
  viewModelJob.cancel()
 }
 fun saveReminder(reminder:Reminder){
  uiScope.launch { _idInserted.value=repository.saveReminder(reminder) }
 }
 fun getReminderById(idReminder:Long){
  uiScope.launch { _reminderById.value= repository.getReminderById(idReminder)
  }
 }
 fun updateReminder(reminder:Reminder){
  uiScope.launch { _updatedRow.value = repository.updateReminder(reminder)
    }
  }
 fun updateReminderList(reminder: Reminder){
  val index = _remindersList.value.indexOf(reminder)
  _remindersList.value[index] = reminder
 }
 fun getAllReminders(){
  uiScope.launch { _remindersList.value=repository.getAllReminders() }
 }
 fun deleteReminder(reminder:Reminder){
  uiScope.launch {
   repository.deleteReminder(reminder.id)
   _remindersList.update {
     val mutableList  = it.toMutableList()
     mutableList.remove(reminder)
     mutableList

   }

  }
 }
}