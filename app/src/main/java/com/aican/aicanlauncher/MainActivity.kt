package com.aican.aicanlauncher

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.SQLite.DatabaseHelper
import com.aican.aicanlauncher.adapterClass.AddedAppAdapter
import com.aican.aicanlauncher.blockNotification.customViewGroup
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.ImageSaver
import com.aican.aicanlauncher.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: AddedAppAdapter
    lateinit var databaseHelper: DatabaseHelper
    lateinit var arrayList: ArrayList<AppItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
//        localLayoutParams.flags =
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // this is to enable the notification to recieve touch events
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or  // Draws over status bar
//                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        localLayoutParams.height = (50 * resources.displayMetrics.scaledDensity).toInt()
//        localLayoutParams.format = PixelFormat.TRANSPARENT
//        val view = customViewGroup(this)
//        manager.addView(view, localLayoutParams)

        arrayList = ArrayList()
        databaseHelper = DatabaseHelper(this@MainActivity)
//        adapter = AddedAppAdapter(this)

//        updateRecView()
        getAllAppData()

        updateRecView()
        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.

//        preventStatusBarExpansion(this@MainActivity)

        binding.webBrowser.setOnClickListener {
            startActivity(Intent(this@MainActivity, WebBrowser::class.java))
        }

        binding.settings.setOnClickListener {
            startActivity(Intent(this@MainActivity, Settings::class.java))
        }

        binding.refresh.setOnRefreshListener {
            arrayList = ArrayList()
            getAllAppData()
            updateRecView()
            binding.refresh.isRefreshing = false
        }
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
        adapter = AddedAppAdapter(this@MainActivity, arrayList)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {

    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        Log.d("tag", "window focus changed")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            collapseNow()
//        }
//    }
//
//    lateinit var collapseNotificationHandler: Handler
//    fun collapseNow() {
//        try {
//            // Initialize 'collapseNotificationHandler'
//            if (collapseNotificationHandler == null) {
//                collapseNotificationHandler = Handler()
//            }
//
//            // Post a Runnable with some delay - currently set to 300 ms
//            collapseNotificationHandler.postDelayed(object : Runnable {
//                override fun run() {
//
//                    // Use reflection to trigger a method from 'StatusBarManager'
//                    val statusBarService = getSystemService("statusbar")
//                    var statusBarManager: Class<*>? = null
//                    try {
//                        statusBarManager = Class.forName("android.app.StatusBarManager")
//                    } catch (e: ClassNotFoundException) {
//                        e.printStackTrace()
//                    }
//                    var collapseStatusBar: Method? = null
//                    try {
//                        // Prior to API 17, the method to call is 'collapse()'
//                        // API 17 onwards, the method to call is `collapsePanels()`
//                        collapseStatusBar = if (Build.VERSION.SDK_INT > 16) {
//                            statusBarManager!!.getMethod("collapsePanels")
//                        } else {
//                            statusBarManager!!.getMethod("collapse")
//                        }
//                    } catch (e: NoSuchMethodException) {
//                        e.printStackTrace()
//                    }
//                    collapseStatusBar?.setAccessible(true)
//                    try {
//                        collapseStatusBar?.invoke(statusBarService)
//                    } catch (e: IllegalArgumentException) {
//                        e.printStackTrace()
//                    } catch (e: IllegalAccessException) {
//                        e.printStackTrace()
//                    } catch (e: InvocationTargetException) {
//                        e.printStackTrace()
//                    }
//                    // Currently, the delay is 10 ms. You can change this
//                    // value to suit your needs.
//                    collapseNotificationHandler.postDelayed(this, 10L)
//                }
//            }, 10L)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

//    fun preventStatusBarExpansion(context: Context) {
//        val manager = context.applicationContext
//            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val activity = context as Activity
//        val localLayoutParams = WindowManager.LayoutParams()
//        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
//        localLayoutParams.gravity = Gravity.TOP
//        localLayoutParams.flags =
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // this is to enable the notification to recieve touch events
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or  // Draws over status bar
//                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        val resId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
//        var result = 0
//        if (resId > 0) {
//            result = activity.resources.getDimensionPixelSize(resId)
//        }
//        localLayoutParams.height = result
//        localLayoutParams.format = PixelFormat.TRANSPARENT
//        val view = customViewGroup(context)
//        manager.addView(view, localLayoutParams)
//    }
//
//    class customViewGroup(context: Context?) : ViewGroup(context) {
//        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}
//        override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//            Log.v("customViewGroup", "**********Intercepted")
//            return true
//        }
//    }

}