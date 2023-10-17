package com.aican.aicanlauncher.dataClass

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addedAppsTable")
class RoomAppItems(
    @ColumnInfo(name = "label")
    var label: CharSequence, // package name
    @ColumnInfo(name = "appName")
    var appName: CharSequence, // app name
    @ColumnInfo(name = "icon")
    var icon: Drawable // app icon
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
