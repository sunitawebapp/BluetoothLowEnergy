package com.srcodecorner.bluetoothlowenergy.ui.Fragment.HomeFragmentDetails

import android.Manifest
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
import androidx.navigation.fragment.findNavController
import com.srcodecorner.bluetoothlowenergy.R
import com.srcodecorner.bluetoothlowenergy.databinding.FragmentHomeBinding
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.getBluetoothAdapter
import com.srcodecorner.bluetoothlowenergy.utils.BluetoothHelper.initBluetoothAdapter
import com.srcodecorner.bluetoothlowenergy.utils.Constents
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
class HomeFragment : Fragment() ,View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding : FragmentHomeBinding? =null

    private var scanning = false
    private val handler = Handler()
    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000000



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
        val leScanCallback: ScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                //   leDeviceListAdapter.addDevice(result.device)
                //  leDeviceListAdapter.notifyDataSetChanged()
                Log.d(TAG, "onScanResult: "+result.device.address)
            }
        }
        initBluetoothAdapter(requireActivity())
        var   filters :MutableList<ScanFilter> = java.util.ArrayList()
        val bluetoothLeScanner = getBluetoothAdapter()?.bluetoothLeScanner
      ///  UserPermissionFunctions.checkBluetoothScanPermission(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
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
                requireActivity(),
                arrayOf(
                    Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_SCAN
                ),
                Constents.REQUEST_BLUETOOTH_SCAN_PERMISSION
            )
            return
        }
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
        super.onViewCreated(view, savedInstanceState)
    }

    fun viewOnClickListener(){
        binding?.let { view->
            view.fabAddDevice.setOnClickListener(this@HomeFragment)
        }
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
