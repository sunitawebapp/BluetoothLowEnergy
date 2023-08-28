package com.srcodecorner.bluetoothlowenergy.utils

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.os.Handler
import android.os.ParcelUuid
import android.util.Log
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions.checkBluetoothFineLocationPermission
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions.checkBluetoothScanPermission
import java.util.*
import kotlin.collections.ArrayList

private val TAG = "BluetoothHelper"

object BluetoothHelper {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var scanning = false
    private val handler = Handler()
    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000
    var scannedDevicesLIst : MutableList<ScannedDevices>?= ArrayList()


    fun initBluetoothAdapter(activity : Activity) : Boolean{
        val bluetoothManager: BluetoothManager = activity.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter =bluetoothManager.getAdapter()
        if (bluetoothAdapter == null) {
            Log.d("onCreate", "Unable to obtain a BluetoothAdapter.")
            return false
        }
        getBluetoothAdapter()
        return true
    }

    fun getBluetoothAdapter(): BluetoothAdapter?{
        return bluetoothAdapter
    }


    fun getScannedDeviceList()  : MutableList<ScannedDevices>?{
        return scannedDevicesLIst
    }

     fun scanLeDevice(leScanCallback: ScanCallback,activity : Activity) {
         var   filters :MutableList<ScanFilter> = java.util.ArrayList()
         val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
        checkBluetoothScanPermission(activity)
        checkBluetoothFineLocationPermission(activity)
         val settings = ScanSettings.Builder()
             .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
             .build()


         filters.add(ScanFilter.Builder().setServiceUuid(ParcelUuid(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"))).build())  // filter only show chair ble devices

         if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false

                bluetoothLeScanner?.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            scanning = true
            bluetoothLeScanner?.startScan(filters,settings,leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }



}
