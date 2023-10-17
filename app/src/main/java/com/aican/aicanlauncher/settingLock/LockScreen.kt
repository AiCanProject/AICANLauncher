package com.aican.aicanlauncher.settingLock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aican.aicanlauncher.DashboardSettings
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.databinding.ActivityDashboardBinding
import com.aican.aicanlauncher.databinding.ActivityLockScreenBinding
import com.aican.aicanlauncher.util.Source

class LockScreen : AppCompatActivity() {

    lateinit var binding: ActivityLockScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPreference1 = getSharedPreferences(
            Source.ID_PASSWORD,
            AppCompatActivity.MODE_PRIVATE
        )

        val uid: String = sharedPreference1.getString(Source.ID, "").toString()

        binding.settings.setOnClickListener {
            if (uid == "") {
                startActivity(Intent(this, DashboardSettings::class.java))
            } else {
                val i = Intent(this, LockScreen::class.java)
                i.putExtra("activity", "Settings")
                startActivity(i)
            }
        }

        val i = intent.getStringExtra("activity").toString()

        val sharedPreference = getSharedPreferences(Source.ID_PASSWORD, MODE_PRIVATE)

        val id: String = sharedPreference.getString(Source.ID, "").toString()
        val pass: String = sharedPreference.getString(Source.PASSWORD, "").toString()
        binding.confirmIDPassAuth.setOnClickListener {
            val idText = binding.etIDAuth.text.toString()
            val passwordText = binding.etPasswordAuth.text.toString()
            if (idText.trim() == "" || passwordText.trim() == "") {
                if (idText.trim() == "") {
                    binding.etIDAuth.error = "Enter your id"
                }
                if (passwordText.trim() == "") {
                    binding.etPasswordAuth.error = "Enter your password"
                }

            } else {
                if (idText == id && passwordText == pass) {
                    if (i == "Settings") {
                        startActivity(Intent(this, DashboardSettings::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Wrong credentials", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}