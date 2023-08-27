package com.srcodecorner.bluetoothlowenergy

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.findBleDevice
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions.setupBluetoothEnable


private val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkBluetoothEnable()
        //findBleDevice(this)
    }
   fun checkBluetoothEnable(){
       setupBluetoothEnable(this)
   }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}
