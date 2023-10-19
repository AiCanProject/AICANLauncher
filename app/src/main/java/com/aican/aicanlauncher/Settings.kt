package com.aican.aicanlauncher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.aican.aicanlauncher.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.decorView.apply {
            // Hide the status bar
            systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.addApps.setOnClickListener {
            startActivity(Intent(this@Settings, AppsListActivity::class.java))
        }

        binding.connectWifi.setOnClickListener {
            startActivity(Intent(this@Settings, ConnectWifi::class.java))
        }

    }
}