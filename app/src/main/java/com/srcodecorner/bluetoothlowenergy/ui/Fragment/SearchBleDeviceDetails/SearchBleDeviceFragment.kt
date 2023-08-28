package com.srcodecorner.bluetoothlowenergy.ui.Fragment.SearchBleDeviceDetails

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import com.srcodecorner.bluetoothlowenergy.MainActivity
import com.srcodecorner.bluetoothlowenergy.Model.ConnectedDevices
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.R
import com.srcodecorner.bluetoothlowenergy.databinding.FragmentSearchBleDeviceBinding
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper
import com.srcodecorner.bluetoothlowenergy.utils.Constents.REQUEST_ENABLE_BT
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchBleDeviceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private val TAG ="SearchBleDeviceFragment"
class SearchBleDeviceFragment : Fragment() {
    lateinit var binding : FragmentSearchBleDeviceBinding
    lateinit var availableBleDetailsAdapter : AvailableBleDetailsAdapter
    var scannedDevicesLIst : MutableList<ScannedDevices>?= null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBleDeviceBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchBleDevice()
        setAdapter()
        super.onViewCreated(view, savedInstanceState)
    }
    fun searchBleDevice(){
        // Device scan callback.
        val leScanCallback: ScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                BluetoothHelper.scannedDevicesLIst?.clear()
                //   leDeviceListAdapter.addDevice(result.device)
                //  leDeviceListAdapter.notifyDataSetChanged()
                UserPermissionFunctions.checkBluetoothConnectPermission(requireActivity())
                BluetoothHelper.scannedDevicesLIst?.add(
                    ScannedDevices(result.device.name,
                        result.device.address)
                )
                BluetoothHelper.getScannedDeviceList()

                Log.d(com.srcodecorner.bluetoothlowenergy.ui.Fragment.HomeFragmentDetails.TAG, "onScanResult: "+result.device.name)

            }
        }
        BluetoothHelper.scanLeDevice(leScanCallback, requireActivity())
        scannedDevicesLIst= BluetoothHelper.getScannedDeviceList()
    }
    fun setAdapter(){
        availableBleDetailsAdapter= AvailableBleDetailsAdapter(scannedDevicesLIst,{ConnectedDevices,Int->
            listRowItemClicked(ConnectedDevices,Int)
        })
        binding.rvDevices.adapter= availableBleDetailsAdapter
    }
    fun listRowItemClicked(connectedDevices: ConnectedDevices,pos : Int){
        Log.d(TAG, "listRowItemClicked: "+connectedDevices)
        (activity as MainActivity).connectToBle(connectedDevices.deviceAddress)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchBleDeviceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchBleDeviceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    
}
