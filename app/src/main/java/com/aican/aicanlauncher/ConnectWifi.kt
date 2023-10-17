package com.aican.aicanlauncher

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aican.aicanlauncher.wifiReceiver.WifiReceiver
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.security.Permissions


class ConnectWifi : AppCompatActivity() {

    lateinit var recView: RecyclerView
    lateinit var wifiManager: WifiManager
    private val MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1
    lateinit var receiverWifi: WifiReceiver
    lateinit var results: List<ScanResult>
    var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_wifi)
        supportActionBar?.hide()

        recView = findViewById(R.id.recView)



        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
//            Toast.makeText(getApplicationContext(), "Turning WiFi ON...", Toast.LENGTH_LONG).show();
            wifiManager.isWifiEnabled = true
        }

//
//        if (ActivityCompat.checkSelfPermission(
//                this@ConnectWifi,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this@ConnectWifi,
//                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//                MY_PERMISSIONS_ACCESS_COARSE_LOCATION
//            )
//        } else {
//            wifiManager.startScan()
//        }

        checkPermissions()

    }

    private fun checkPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
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

    override fun onPostResume() {
        super.onPostResume()
        receiverWifi = WifiReceiver(wifiManager, recView)
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(receiverWifi, intentFilter)

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
                    this@ConnectWifi,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
//                Toast.makeText(AvailableWifiDevices.this, "location turned off", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                    this@ConnectWifi, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
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
        unregisterReceiver(receiverWifi)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(AvailableWifiDevices.this, "permission granted", Toast.LENGTH_SHORT).show();
                wifiManager.startScan()
            } else {
                Toast.makeText(
                    this@ConnectWifi,
                    "permission not granted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}