package com.srcodecorner.bluetoothlowenergy.ui.Activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.R
import com.srcodecorner.bluetoothlowenergy.Service.BleService
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper

import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.initBluetoothAdapter
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions.setupBluetoothEnable


private val TAG="MainActivity"
class MainActivity : AppCompatActivity() {

    var bleService : BleService?= null
   internal val mainViewModel : MainViewModel by viewModels()

    // Code to manage Service lifecycle.
    private var  serviceConnection: ServiceConnection? = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            service: IBinder
        ) {
            bleService = (service as BleService.LocalBinder).getService()
            bleService?.let { bluetooth ->
                if (!initBluetoothAdapter(this@MainActivity)) {
                    Log.e(TAG, "Unable to initialize Bluetooth")
                    finish()
                }
                // perform device connection

            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bleService = null
        }
    }

    private val gattUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "onReceive: "+intent.action)
            when (intent.action) {
                BleService.ACTION_GATT_CONNECTED -> {
                  //  connected = true
                   // updateConnectionState(R.string.connected)
                    mainViewModel.deviceConnectionLivedata.postValue(true)
                }
                BleService.ACTION_GATT_DISCONNECTED -> {
                   // connected = false
                  //  updateConnectionState(R.string.disconnected)

                    mainViewModel.deviceConnectionLivedata.postValue(false)
                }
            }
        }
    }

    // Device scan callback.
    val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            BluetoothHelper.scannedDevicesLIst?.clear()
            //   leDeviceListAdapter.addDevice(result.device)
            //  leDeviceListAdapter.notifyDataSetChanged()
            UserPermissionFunctions.checkBluetoothConnectPermission(this@MainActivity)
            BluetoothHelper.scannedDevicesLIst?.add(
                ScannedDevices(result.device.name,
                    result.device.address)
            )
            BluetoothHelper.getScannedDeviceList()

            Log.d(TAG, "onScanResult: "+result.device.name)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkBluetoothEnable()
        BluetoothHelper.findBleDevice(this)


        val gattServiceIntent = Intent(this, BleService::class.java)
        serviceConnection?.let { bindService(gattServiceIntent, it, Context.BIND_AUTO_CREATE) }



    }
   fun checkBluetoothEnable(){
       setupBluetoothEnable(this)
   }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun connectToBle(address: String) {
        bleService?.let {
            //  viewModel.showConnectingDialog(true)
            it.connect(address)

        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter())
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter? {
        return IntentFilter().apply {
            addAction(BleService.ACTION_GATT_CONNECTED)
            addAction(BleService.ACTION_GATT_DISCONNECTED)
        }
    }

}
