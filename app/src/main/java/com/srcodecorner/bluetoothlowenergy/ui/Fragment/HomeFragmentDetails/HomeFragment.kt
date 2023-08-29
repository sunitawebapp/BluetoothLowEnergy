package com.srcodecorner.bluetoothlowenergy.ui.Fragment.HomeFragmentDetails

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.ParcelUuid
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.srcodecorner.bluetoothlowenergy.Model.ScannedDevices
import com.srcodecorner.bluetoothlowenergy.R
import com.srcodecorner.bluetoothlowenergy.databinding.FragmentHomeBinding
import com.srcodecorner.bluetoothlowenergy.ui.Activity.MainActivity
import com.srcodecorner.bluetoothlowenergy.ui.Activity.MainViewModel
import com.srcodecorner.bluetoothlowenergy.ui.Fragment.SearchBleDeviceDetails.AvailableBleDetailsAdapter
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.getScannedDeviceList

import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.scanLeDevice
import com.srcodecorner.bluetoothlowenergy.utils.Constents
import com.srcodecorner.bluetoothlowenergy.utils.HelperFunction.toast
import com.srcodecorner.bluetoothlowenergy.utils.UserPermissionFunctions
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
val TAG ="HomeFragment"
class HomeFragment : Fragment() ,View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding : FragmentHomeBinding? =null
    lateinit var availableBleDetailsAdapter : ConnectedDeviceAdapter

    private var scanning = false
    private val handler = Handler()
    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000000

    val mainViewModel : MainViewModel by viewModels()



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
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewOnClickListener()

        mainViewModel.deviceConnectionLivedata.observe(viewLifecycleOwner){
            toast(requireActivity(),it.toString())
        }

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
                if ("A4:DA:32:67:8F:77".equals(BluetoothHelper.scannedDevicesLIst?.get(0)?.deviceAddress)){
                    (activity as MainActivity).connectToBle("A4:DA:32:67:8F:77")
                    setAdapter()
                }

                Log.d(TAG, "onScanResult: "+result.device.name)

            }
        }
        scanLeDevice(leScanCallback,requireActivity())
        Log.d(TAG, "onViewCreated: "+ BluetoothHelper.scannedDevicesLIst)

        super.onViewCreated(view, savedInstanceState)
    }

    fun viewOnClickListener(){
        binding?.let { view->
            view.fabAddDevice.setOnClickListener(this@HomeFragment)
        }
    }
    fun setAdapter(){
        //availableBleDetailsAdapter= ConnectedDeviceAdapter()
       // binding?.rvDevices?.adapter= availableBleDetailsAdapter
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View?) {
        when(view){
            binding?.fabAddDevice ->{
                findNavController().navigate(R.id.searchBleDeviceFragment)
            }
        }
    }
}
