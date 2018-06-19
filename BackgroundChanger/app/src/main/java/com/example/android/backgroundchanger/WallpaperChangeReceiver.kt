package com.example.android.backgroundchanger

import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast

class WallpaperChangeReceiver: BroadcastReceiver(){

    val myWpManager = WpManager
    private val interval = 10 * 1000
    override fun onReceive(context: Context, intent: Intent){
        var handler = Handler()

        var runnable =  Runnable {
            fun run() {
                try {
                    val newWallpaper = myWpManager.nextWallpaper()
                    val myWallpaperManager = WallpaperManager.getInstance(context)
                    val imageSelected = MediaStore.Images.Media.getBitmap(context.contentResolver, newWallpaper)
                    myWallpaperManager.setBitmap(imageSelected)
                    Toast.makeText(context, "Wallpaper changed successfully", Toast.LENGTH_LONG).show()
                }catch (e: Exception){
                    Toast.makeText(context, "Wallpaper not changed :(", Toast.LENGTH_LONG).show()
                }
            }
        }
        handler.postAtTime(runnable , System.currentTimeMillis()+ this.interval)
        handler.postDelayed(runnable, this.interval.toLong())
        Toast.makeText(context, "Received the change", Toast.LENGTH_LONG).show()
    }
}