package com.srcodecorner.bluetoothlowenergy.utils

import android.app.Activity
import android.widget.Toast

object HelperFunction {
    fun toast(activity: Activity,text : String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}
