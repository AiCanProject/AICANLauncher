package com.aican.aicanlauncher.fragments

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.SQLite.DatabaseHelper
import com.aican.aicanlauncher.adapterClass.AddedAppAdapterSame
import com.aican.aicanlauncher.adapterClass.AllAppsAdapter
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.ImageSaver
import com.aican.aicanlauncher.interfaces.ReloadRecView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SelectAppFrag : Fragment(), ReloadRecView {
    private var param1: String? = null
    private var param2: String? = null


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
        return inflater.inflate(R.layout.fragment_select_app, container, false)
    }

    lateinit var manager: PackageManager
    lateinit var apps: ArrayList<AppItems>
    lateinit var allAppAdapter: AllAppsAdapter
    lateinit var appLists: RecyclerView
    lateinit var selectedAppList: RecyclerView
    lateinit var databaseHelper: DatabaseHelper
    lateinit var arrayList: ArrayList<AppItems>
    lateinit var adapter: AddedAppAdapterSame
    lateinit var refresh: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appLists = view.findViewById(R.id.appLists)
        refresh = view.findViewById(R.id.refresh)
        selectedAppList = view.findViewById(R.id.selectedAppList)
        databaseHelper = DatabaseHelper(context)
        arrayList = ArrayList()

        loadApps()
        loadRecView()
        getAllAppData()
        updateRecView()

        refresh.setOnRefreshListener {
            arrayList = ArrayList()
            getAllAppData()
            updateRecView()
            refresh.isRefreshing = false
        }
    }

    private fun loadRecView() {
        allAppAdapter = AllAppsAdapter(requireContext(), apps, databaseHelper, arrayList, this)
        appLists.adapter = allAppAdapter
        allAppAdapter.notifyDataSetChanged()

    }

    private fun updateRecView() {
        adapter = AddedAppAdapterSame(requireContext(), arrayList, databaseHelper,this)
        selectedAppList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun getAllAppData() {
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        val calibCSV = db.rawQuery("SELECT * FROM Appdetails", null)

        while (calibCSV.moveToNext()) {
            val appId = calibCSV.getString(calibCSV.getColumnIndex("appId"))
            val appName = calibCSV.getString(calibCSV.getColumnIndex("appName"))
            val appLogo = calibCSV.getBlob(calibCSV.getColumnIndex("appLogo"))
            val type = calibCSV.getString(calibCSV.getColumnIndex("type"))
            val draw = ImageSaver.getImage(appLogo)

            val d: Drawable = BitmapDrawable(resources, draw)

            val appItems = AppItems(appId, appName, d, type)
            arrayList.add(appItems)
        }


    }


    private fun loadApps() {
        try {
            manager = context?.packageManager!!
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

                if (manager.getLaunchIntentForPackage(infos.packageName) != null) {
                    val app =
                        AppItems(
                            infos.packageName,
                            infos.loadLabel(manager),
                            infos.loadIcon(manager),
                            "1"
                        )
                    apps.add(app)

                } else {
                    //System App
                }

            }

            Toast.makeText(
                context,
                apps.size.toString(),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace();
            Toast.makeText(context, "Not installed " + e.printStackTrace(), Toast.LENGTH_SHORT)
                .show();

        }

    }

    override fun reloadRecView() {
        arrayList = ArrayList()
        getAllAppData()
        updateRecView()
    }

    override fun reloadSystemAppRecyclerView() {
        apps = ArrayList()
        loadApps()
        loadRecView()
//        getAllAppData()
    }

}