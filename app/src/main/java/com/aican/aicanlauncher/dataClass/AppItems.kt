package com.aican.aicanlauncher.dataClass

import android.graphics.drawable.Drawable

data class AppItems(
    var label: CharSequence, // package name
    var name: CharSequence, // app name
    var icon: Drawable,// app icon
    var type: String

)
