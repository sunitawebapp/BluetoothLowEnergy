package com.srcodecorner.bluetoothlowenergy.ui.Activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.srcodecorner.bluetoothlowenergy.Service.BleService

class MainViewModel : ViewModel() {
  val deviceConnectionLivedata : MutableLiveData<Boolean> =MutableLiveData()
  var bleServiceLiveData: MutableLiveData<BleService> =MutableLiveData()

  fun getConnectionStatus(isconnected : Boolean){
     deviceConnectionLivedata.value=isconnected
  }
    fun getbleService() :  MutableLiveData<BleService>{
       return bleServiceLiveData
    }
}
