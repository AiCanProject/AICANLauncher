package com.aican.aicanlauncher

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.SQLite.DatabaseHelper
import com.aican.aicanlauncher.adapterClass.AddedAppAdapter
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.ImageSaver
import com.aican.aicanlauncher.databinding.ActivityDashboardBinding
import com.aican.aicanlauncher.settingLock.LockScreen
import com.aican.aicanlauncher.util.Source

class Dashboard : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding
    lateinit var adapter: AddedAppAdapter
    lateinit var databaseHelper: DatabaseHelper
    lateinit var arrayList: ArrayList<AppItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        window.decorView.apply {
            // Hide the status bar
            systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        arrayList = ArrayList()
        databaseHelper = DatabaseHelper(this@Dashboard)
//        adapter = AddedAppAdapter(this)
        val d: Drawable = resources.getDrawable(R.drawable.publics)
//                    val bitmap =  (d as BitmapDrawable).bitmap
        val bitmap = ImageSaver.getBitmapFromDrawable(d)

        val imageD = ImageSaver.getBytes(bitmap)
        databaseHelper.checkDelete("com.website")

        databaseHelper.insertAppData("com.website", "AICAN Web Page", imageD, "2")
//        updateRecView()
        getAllAppData()

        updateRecView()
        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.

//        preventStatusBarExpansion(this@MainActivity)

//        binding.webBrowser.setOnClickListener {
//            startActivity(Intent(this@Dashboard, WebBrowser::class.java))
//        }

        val sharedPreference = getSharedPreferences(Source.ID_PASSWORD, MODE_PRIVATE)

        val id: String = sharedPreference.getString(Source.ID, "").toString()
        val pass: String = sharedPreference.getString(Source.PASSWORD, "").toString()



        binding.settings.setOnClickListener {
            if (id == "") {
                startActivity(Intent(this@Dashboard, DashboardSettings::class.java))
            } else {
                val i = Intent(this@Dashboard, LockScreen::class.java)
                i.putExtra("activity", "Settings")
                startActivity(i)
            }
        }

        binding.webBrowser.setOnClickListener {
            startActivity(Intent(this@Dashboard, WebBrowser::class.java))
        }

        binding.refresh.setOnRefreshListener {
            arrayList = ArrayList()
            getAllAppData()
            updateRecView()
            binding.refresh.isRefreshing = false
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Reapply the flags if focus is regained (e.g., after a dialog is closed)
            window.decorView.apply {
                systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Intercept touch events to prevent status bar from being revealed
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getAllAppData() {
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        val calibCSV = db.rawQuery("SELECT * FROM Appdetails", null)

        while (calibCSV.moveToNext()) {
            val appId = calibCSV.getString(calibCSV.getColumnIndex("appId"))
            val appName = calibCSV.getString(calibCSV.getColumnIndex("appName"))
            val type = calibCSV.getString(calibCSV.getColumnIndex("type"))
            val appLogo = calibCSV.getBlob(calibCSV.getColumnIndex("appLogo"))

            val draw = ImageSaver.getImage(appLogo)

            val d: Drawable = BitmapDrawable(resources, draw)

            var appItems = AppItems(appId, appName, d, type)
            arrayList.add(appItems)
        }


    }

    fun updateRecView() {
        adapter = AddedAppAdapter(this@Dashboard, arrayList)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {

    }

}