package com.srcodecorner.bluetoothlowenergy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.srcodecorner.bluetoothlowenergy.Service.BleService

class BleReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BleService.ACTION_GATT_CONNECTED -> {
                //  connected = true
                // updateConnectionState(R.string.connected)
            }
            BleService.ACTION_GATT_DISCONNECTED -> {
                // connected = false
                //  updateConnectionState(R.string.disconnected)
            }
        }
    }
}