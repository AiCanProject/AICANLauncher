package com.aican.aicanlauncher.fragments

import android.Manifest
import android.app.Activity
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.wifiReceiver.WifiReceiver
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WifiFrag : Fragment() {
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

    lateinit var recView: RecyclerView
    lateinit var wifiManager: WifiManager
    private val MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1
    lateinit var receiverWifi: WifiReceiver
    lateinit var results: List<ScanResult>
    var size = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recView = view.findViewById(R.id.wifiRec)

        wifiManager =
            requireContext().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
//            Toast.makeText(getApplicationContext(), "Turning WiFi ON...", Toast.LENGTH_LONG).show();
            wifiManager.isWifiEnabled = true
        }

        checkPermissions()

    }


    override fun onResume() {
        super.onResume()
        receiverWifi = WifiReceiver(wifiManager, recView)
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        requireContext().registerReceiver(receiverWifi, intentFilter)

//        registerReceiver(object : BroadcastReceiver() {
//            override fun onReceive(c: Context?, intent: Intent?) {
//                results = wifiManager.scanResults
//                size = results.size
//            }
//        }, intentFilter)
        getWifi()
    }

    private fun getWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Toast.makeText(AvailableWifiDevices.this, "version >= marshmallow", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
//                Toast.makeText(AvailableWifiDevices.this, "location turned off", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                    requireContext() as Activity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_ACCESS_COARSE_LOCATION
                )
            } else {
//                Toast.makeText(AvailableWifiDevices.this, "location turned on", Toast.LENGTH_SHORT).show();
                wifiManager.startScan()
            }
        } else {
//            Toast.makeText(AvailableWifiDevices.this, "scanning", Toast.LENGTH_SHORT).show();
            wifiManager.startScan()
        }
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiverWifi)
    }


    private fun checkPermissions() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    if (!report.areAllPermissionsGranted()) {
                        checkPermissions()
                    } else {
//                        loadDataWithPersmission()
                        wifiManager.startScan()

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi, container, false)
    }


}