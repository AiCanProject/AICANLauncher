package com.aican.aicanlauncher

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.SQLite.DatabaseHelper
import com.aican.aicanlauncher.adapterClass.AddedAppAdapter
import com.aican.aicanlauncher.adapterClass.AllAppsAdapter
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.ImageSaver
import com.aican.aicanlauncher.dataClass.RoomAppItems
import com.aican.aicanlauncher.interfaces.ReloadRecView


class AppsListActivity : AppCompatActivity(), ReloadRecView {

    lateinit var manager: PackageManager
    lateinit var apps: ArrayList<AppItems>
    lateinit var listView: ListView
    lateinit var databaseHelper: DatabaseHelper
    lateinit var allAppAdapter: AllAppsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)

        databaseHelper = DatabaseHelper(this@AppsListActivity)

        listView = findViewById(R.id.listView)

        loadApps()
        loadListView()

    }

    fun getAllApps() {
        // get list of all the apps installed
        val packList = packageManager.getInstalledPackages(0)
        val apps = arrayOfNulls<String>(packList.size)
        for (i in packList.indices) {
            val packInfo = packList[i]
            apps[i] = packInfo.applicationInfo.loadLabel(packageManager).toString()
        }
        // set all the apps name in list view
//        listView.adapter = ArrayAdapter(this@AppsListActivity, android.R.layout.simple_list_item_1, apps)
        // write total count of apps available.
//        text.text = packList.size.toString() + " Apps are installed"
    }

    private fun loadApps() {
        try {
            manager = packageManager
            apps = ArrayList()

            val i = Intent(Intent.ACTION_VIEW, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)

//            val availableActivities: List<ResolveInfo> = manager.queryIntentActivities(i, 0)
//
//            for (ri: ResolveInfo in availableActivities) {
//                val app = AppItems(
//                    ri.activityInfo.packageName,
//                    ri.loadLabel(manager), ri.loadIcon(manager)
//                )
//                apps.add(app)
//            }

            val info = manager.getInstalledApplications(PackageManager.GET_META_DATA)

            for (infos: ApplicationInfo in info) {
                val app =
                    AppItems(infos.packageName, infos.loadLabel(manager), infos.loadIcon(manager),"1")
                apps.add(app)
            }

            Toast.makeText(
                this@AppsListActivity,
                apps.size.toString(),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace();
            Toast.makeText(this, "Not installed " + e.printStackTrace(), Toast.LENGTH_SHORT).show();

        }

    }

    private fun loadListView() {
        allAppAdapter = AllAppsAdapter(this@AppsListActivity, apps, databaseHelper, apps, this)
        val adapter: ArrayAdapter<AppItems> =
            object : ArrayAdapter<AppItems>(this@AppsListActivity, R.layout.item, apps) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    var views: View
                    if (convertView == null) {
                        views = layoutInflater.inflate(R.layout.item, null)
                    }
                    views = layoutInflater.inflate(R.layout.item, null)
                    val appIcon: ImageView = views!!.findViewById(R.id.icon)
                    val appName: TextView = views!!.findViewById(R.id.name)
                    val addAppBtn: ImageView = views!!.findViewById(R.id.addAppBtn)
                    appIcon.setImageDrawable(apps[position].icon)
                    appName.text = apps[position].name

                    val d: Drawable = apps[position].icon
//                    val bitmap =  (d as BitmapDrawable).bitmap
                    val bitmap = ImageSaver.getBitmapFromDrawable(d)

                    var imageD = ImageSaver.getBytes(bitmap)
                    addAppBtn.setOnClickListener {
                        var roomAppItems =
                            RoomAppItems(
                                apps.get(position).label,
                                apps.get(position).name,
                                apps.get(position).icon
                            )

                        databaseHelper.checkDelete(apps.get(position).label.toString())
                        databaseHelper.insertAppData(
                            apps.get(position).label.toString(),
                            apps.get(position).name.toString(), imageD,
                            "1"
                        )
//                        if (!databaseHelper.checkDelete(apps.get(position).label.toString())) {
//                            databaseHelper.insertAppData(
//                                apps.get(position).label.toString(),
//                                apps.get(position).name.toString(), imageD
//                            )
//                        } else {
//                            Toast.makeText(
//                                this@AppsListActivity,
//                                "App already exists",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
                    }

                    return views

                }
            }
        listView.adapter = adapter

        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@AppsListActivity,
                    apps.get(position).label.toString(),
                    Toast.LENGTH_SHORT
                ).show();

                val intent: Intent? =
                    packageManager.getLaunchIntentForPackage(apps.get(position).label.toString())
                startActivity(intent)
            }

        })


    }

    override fun reloadRecView() {
        TODO("Not yet implemented")
    }

    override fun reloadSystemAppRecyclerView() {
        TODO("Not yet implemented")
    }


}