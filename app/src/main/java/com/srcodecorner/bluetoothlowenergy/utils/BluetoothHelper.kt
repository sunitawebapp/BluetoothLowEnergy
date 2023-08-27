package com.srcodecorner.bluetoothlowenergy.utils

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Handler
import android.util.Log
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions.checkBluetoothScanPermission

private val TAG = "BluetoothHelper"

object BluetoothHelper {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var scanning = false
    private val handler = Handler()
    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000


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

    fun findBleDevice(activity : Activity){
       //  val leDeviceListAdapter = LeDeviceListAdapter()
        // Device scan callback.
       /*  val leScanCallback: ScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
             //   leDeviceListAdapter.addDevice(result.device)
              //  leDeviceListAdapter.notifyDataSetChanged()
                Log.d(TAG, "onScanResult: "+result.device.address)
            }
        }*/
     //   scanLeDevice(leScanCallback,activity)
    }

     fun scanLeDevice(leScanCallback: ScanCallback,activity : Activity) {
         val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
        checkBluetoothScanPermission(activity)
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false

                bluetoothLeScanner?.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            scanning = true
            bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }

}
