package com.aican.aicanlauncher.adapterClass

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.SQLite.DatabaseHelper
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.ImageSaver
import com.aican.aicanlauncher.interfaces.ReloadRecView

class AllAppsAdapter(
    var context: Context,
    var arrayList: ArrayList<AppItems>,
    var databaseHelper: DatabaseHelper,
    var addedApps: ArrayList<AppItems>,
    var reloadRecView: ReloadRecView
) :
    RecyclerView.Adapter<AllAppsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appIcon: ImageView = itemView!!.findViewById(R.id.icon)
        val appName: TextView = itemView!!.findViewById(R.id.name)
        val addAppBtn: ImageView = itemView!!.findViewById(R.id.addAppBtn)
        val delete: ImageView = itemView!!.findViewById(R.id.delete)
        var checkAdded = itemView.findViewById<ImageView>(R.id.checkAdded)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = 0
        holder.addAppBtn.visibility = View.GONE
        holder.checkAdded.visibility = View.GONE
        holder.delete.visibility = View.GONE
        for (apps: AppItems in addedApps) {
            if (apps.label == arrayList[position].label) {
                holder.checkAdded.visibility = View.VISIBLE
                i = 1
            }
        }

        holder.appIcon.setImageDrawable(arrayList[position].icon)
        holder.appName.text = arrayList[position].name
        val d: Drawable = arrayList[position].icon
//                    val bitmap =  (d as BitmapDrawable).bitmap
        val bitmap = ImageSaver.getBitmapFromDrawable(d)

        val imageD = ImageSaver.getBytes(bitmap)


        holder.itemView.setOnClickListener {
            if (i == 0) {
                databaseHelper.checkDelete(arrayList.get(position).label.toString())
                databaseHelper.insertAppData(
                    arrayList.get(position).label.toString(),
                    arrayList.get(position).name.toString(), imageD,
                    "1"
                )
                i = 1
                holder.checkAdded.visibility = View.VISIBLE
            } else {
                databaseHelper.checkDelete(arrayList.get(position).label.toString())
                holder.checkAdded.visibility = View.GONE
                i = 0

            }
            reloadRecView.reloadRecView()

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}