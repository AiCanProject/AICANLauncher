package com.aican.aicanlauncher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aican.aicanlauncher.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.addApps.setOnClickListener {
            startActivity(Intent(this@Settings, AppsListActivity::class.java))
        }

        binding.connectWifi.setOnClickListener {
            startActivity(Intent(this@Settings, ConnectWifi::class.java))
        }

    }
}