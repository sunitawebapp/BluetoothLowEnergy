package com.srcodecorner.bluetoothlowenergy.ui.Fragment.HomeFragmentDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.srcodecorner.bluetoothlowenergy.Model.ConnectedDevices
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.databinding.RowBleAvailableDevicesBinding
import com.srcodecorner.bluetoothlowenergy.databinding.RowDeviceListBinding
import com.srcodecorner.bluetoothlowenergy.ui.Fragment.SearchBleDeviceDetails.AvailableBleDetailsAdapter

class ConnectedDeviceAdapter(var scannedDevicesLIst : MutableList<ConnectedDevices>?
) : RecyclerView.Adapter<ConnectedDeviceAdapter.ConnectedDeviceViewHolder>() {
    var connectedDeviceListener : ConnectedDeviceListener?=null
    class ConnectedDeviceViewHolder(var binding: RowDeviceListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    interface ConnectedDeviceListener{
       fun onClickDeleteItem(position : Int)
    }

    fun setAdapterListener(connectedDeviceListener : ConnectedDeviceListener?){
        this.connectedDeviceListener=connectedDeviceListener
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
        if (list?.isconnected == true)  holder.binding.tvDeviceConnectionStatus.text = "Connected"
        else  holder.binding.tvDeviceConnectionStatus.text = "Disconnected"
         holder.binding.imgDelete.setOnClickListener {
             connectedDeviceListener?.onClickDeleteItem(position)
         }

    }

    override fun getItemCount(): Int {
        return scannedDevicesLIst?.size!!
    }


}
