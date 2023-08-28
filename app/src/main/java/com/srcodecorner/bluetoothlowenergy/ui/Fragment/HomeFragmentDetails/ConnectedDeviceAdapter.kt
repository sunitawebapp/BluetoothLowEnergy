package com.srcodecorner.bluetoothlowenergy.ui.Fragment.HomeFragmentDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srcodecorner.bluetoothlowenergy.Model.ConnectedDevices
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.databinding.RowBleAvailableDevicesBinding
import com.srcodecorner.bluetoothlowenergy.databinding.RowDeviceListBinding
import com.srcodecorner.bluetoothlowenergy.ui.Fragment.SearchBleDeviceDetails.AvailableBleDetailsAdapter

class ConnectedDeviceAdapter(var scannedDevicesLIst : MutableList<ScannedDevices>?
) : RecyclerView.Adapter<ConnectedDeviceAdapter.ConnectedDeviceViewHolder>() {
    class ConnectedDeviceViewHolder(var binding: RowDeviceListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectedDeviceViewHolder {
        return ConnectedDeviceViewHolder(RowDeviceListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ConnectedDeviceViewHolder, position: Int) {
        val list= scannedDevicesLIst?.get(position)
        holder.binding.tvDeviceName.text = scannedDevicesLIst?.get(position)?.deviceName ?: ""
        holder.binding.tvDeviceConnectionStatus.text = "Connected"
        holder.itemView.setOnClickListener {
            clicklistener(ConnectedDevices(list!!.deviceName,list!!.deviceAddress,true),position)
        }
    }

    override fun getItemCount(): Int {
        return scannedDevicesLIst?.size!!
    }


}
