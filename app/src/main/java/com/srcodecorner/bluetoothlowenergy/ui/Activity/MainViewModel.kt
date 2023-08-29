package com.srcodecorner.bluetoothlowenergy.ui.Activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
  val deviceConnectionLivedata : MutableLiveData<Boolean> =MutableLiveData()
}
