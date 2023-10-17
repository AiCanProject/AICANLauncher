package com.aican.aicanlauncher.adapterClass

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.WebBrowser
import com.aican.aicanlauncher.dataClass.AppItems
import com.aican.aicanlauncher.dataClass.RoomAppItems

class AddedAppAdapter(var context: Context, var arrayList: ArrayList<AppItems>) :
    RecyclerView.Adapter<AddedAppAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appIcon = itemView.findViewById<ImageView>(R.id.app_icon)
        var appName = itemView.findViewById<TextView>(R.id.app_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.added_apps, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.appIcon.setImageDrawable(arrayList[position].icon)
        holder.appName.setText(arrayList[position].name)

        holder.itemView.setOnClickListener {
            if (arrayList[position].type == "1") {
                Toast.makeText(
                    context,
                    arrayList[position].label.toString(),
                    Toast.LENGTH_SHORT
                ).show();

                val intent: Intent? =
                    context.packageManager.getLaunchIntentForPackage(arrayList.get(position).label.toString())
                context.startActivity(intent)
            } else if (arrayList[position].type == "2") {
                context.startActivity(Intent(context, WebBrowser::class.java))

            }
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}