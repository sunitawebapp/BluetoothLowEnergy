package com.srcodecorner.bluetoothlowenergy.ui.Fragment.SearchBleDeviceDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srcodecorner.bluetoothlowenergy.Model.ConnectedDevices
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.R
import com.srcodecorner.bluetoothlowenergy.databinding.RowBleAvailableDevicesBinding


class AvailableBleDetailsAdapter(var scannedDevicesLIst : MutableList<ScannedDevices>?,
                                 val clicklistener : (ConnectedDevices, Int) -> Unit
) : RecyclerView.Adapter<AvailableBleDetailsAdapter.AvailableBleDetailsViewHolder>() {
    class AvailableBleDetailsViewHolder(var binding: RowBleAvailableDevicesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableBleDetailsViewHolder {
       return AvailableBleDetailsViewHolder(RowBleAvailableDevicesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AvailableBleDetailsViewHolder, position: Int) {
        val list= scannedDevicesLIst?.get(position)
        holder.binding.tvDeviceName.text = scannedDevicesLIst?.get(position)?.deviceName ?: ""
        holder.itemView.setOnClickListener {
            clicklistener(ConnectedDevices(list!!.deviceName,list!!.deviceAddress,true),position)
        }
    }

    override fun getItemCount(): Int {
        return scannedDevicesLIst?.size!!
    }


}