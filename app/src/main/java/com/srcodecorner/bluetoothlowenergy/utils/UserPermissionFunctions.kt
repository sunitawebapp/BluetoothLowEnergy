package com.srcodecorner.bluetoothlowenergy.utils

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.getBluetoothAdapter
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.initBluetoothAdapter
import com.srcodecorner.bluetoothlowenergy.utils.Constents.REQUEST_BLUETOOTH_CONNECT_PERMISSION
import com.srcodecorner.bluetoothlowenergy.utils.Constents.REQUEST_BLUETOOTH_SCAN_PERMISSION
import com.srcodecorner.bluetoothlowenergy.utils.Constents.REQUEST_ENABLE_BT


object UserPermissionFunctions {

    @RequiresApi(Build.VERSION_CODES.M)
    fun setupBluetoothEnable( activity: Activity){
        initBluetoothAdapter(activity).equals(true)?.let {
            if (it) {
                if (getBluetoothAdapter()?.isEnabled == false) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    checkBluetoothConnectPermission(activity)
                    activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                }
            }
        }
    }

    fun checkBluetoothConnectPermission(activity: Activity){
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // Request Bluetooth permissions
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_CONNECT
                ),
                REQUEST_BLUETOOTH_CONNECT_PERMISSION
            )
        } else {
            // Permissions are already granted, proceed with Bluetooth operations
        }
    }

    fun checkBluetoothScanPermission(activity: Activity){
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_SCAN
                ),
                REQUEST_BLUETOOTH_SCAN_PERMISSION
            )
            return
        }
    }

}
