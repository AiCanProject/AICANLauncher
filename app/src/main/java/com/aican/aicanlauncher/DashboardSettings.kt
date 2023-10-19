package com.aican.aicanlauncher

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aican.aicanlauncher.databinding.ActivityDashboardSettingsBinding
import com.aican.aicanlauncher.fragments.*


class DashboardSettings : AppCompatActivity() {

    lateinit var dashboardBinding: ActivityDashboardSettingsBinding
    lateinit var setIDPasswordFrag: SetIDPasswordFrag
    lateinit var setUrlFrag: SetUrlFrag
    lateinit var selectAppFrag: SelectAppFrag
    lateinit var wifiFrag: WifiFrag
    lateinit var resetLauncherFrag: ResetLauncherFrag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardSettingsBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)
        supportActionBar?.hide()

        window.decorView.apply {
            // Hide the status bar
            systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


//        val manager =
//            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val localLayoutParams = WindowManager.LayoutParams()
//        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
//        localLayoutParams.gravity = Gravity.TOP
//
//        val LAYOUT_FLAG: Int
//        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        } else {
//            WindowManager.LayoutParams.TYPE_PHONE
//        }
//
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            LAYOUT_FLAG,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
//
////        localLayoutParams.flags =
////            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // this is to enable the notification to recieve touch events
////                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or  // Draws over status bar
////                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//
//        localLayoutParams.flags = params.flags
//
//        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        localLayoutParams.height = (50 * resources.displayMetrics.scaledDensity).toInt()
//        localLayoutParams.format = PixelFormat.TRANSPARENT
//        val view = customViewGroup(this)
//        manager.addView(view, localLayoutParams)

        setIDPasswordFrag = SetIDPasswordFrag()
        setUrlFrag = SetUrlFrag()
        selectAppFrag = SelectAppFrag()
        wifiFrag = WifiFrag()
        resetLauncherFrag = ResetLauncherFrag()

        loadFragments(setIDPasswordFrag)

        dashboardBinding.setIDPassBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_grey))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_blue))

            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_grey))

            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_grey))

            loadFragments(setIDPasswordFrag)
        }

        dashboardBinding.setIDUrlBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_grey))

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_blue))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_grey))

            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_grey))

            loadFragments(setUrlFrag)
        }

        dashboardBinding.selectAppBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(resources.getColor(R.color.id_blue_grey))
            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_grey))

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_grey))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_blue))

            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_grey))

            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_grey))

            loadFragments(selectAppFrag)
        }

        dashboardBinding.setUpWifiBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(resources.getColor(R.color.id_blue_grey))
            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_grey))

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_grey))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))


            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_blue))


            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_grey))

//            startActivity(Intent(this@DashboardSettings, WifiActivity::class.java))
//            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
//            startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))

//            finishAffinity()

            val intent: Intent? =
                packageManager.getLaunchIntentForPackage("com.example.wificonfig")
            startActivity(intent)

        }

        dashboardBinding.applockBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(resources.getColor(R.color.id_blue_grey))
            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_grey))

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_grey))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_grey))

            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_blue))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_grey))

            val intent: Intent? =
                packageManager.getLaunchIntentForPackage("io.github.subhamtyagi.privacyapplock")
            startActivity(intent)
        }

        dashboardBinding.resetLauncherBlock.setOnClickListener {
            dashboardBinding.setIDpassText.setTextColor(resources.getColor(R.color.id_blue_grey))

            dashboardBinding.setIDpassImg.setImageDrawable(resources.getDrawable(R.drawable.id_wallet_grey))

            dashboardBinding.setUrlText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setUrlImg.setImageDrawable(resources.getDrawable(R.drawable.id_url_grey))

            dashboardBinding.selectAppText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.selectAppImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))


            dashboardBinding.setWifiText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.setWifiImg.setImageDrawable(resources.getDrawable(R.drawable.id_wifi_grey))

            dashboardBinding.applockText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_grey
                )
            )
            dashboardBinding.applockImg.setImageDrawable(resources.getDrawable(R.drawable.id_select_app_grey))

            dashboardBinding.resetLauncherText.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.id_blue_aican
                )
            )
            dashboardBinding.resetLauncherImg.setImageDrawable(resources.getDrawable(R.drawable.id_launcher_blue))

//            Toast.makeText(this@DashboardSettings, "Available Soon", Toast.LENGTH_SHORT).show()
            loadFragments(resetLauncherFrag)

        }

    }

    private fun launchAppChooser() {
        Log.d("Launcher", "launchAppChooser()")
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun loadFragments(fragment: Fragment?): Boolean {
        if (fragment != null) {
            Log.d("navigation", "loadFragments: Frag is loaded")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}