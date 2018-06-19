package com.example.android.backgroundchanger

import android.net.Uri

object WpManager{

    val maxWallpapers = 10
    var wallpaperMutableList = mutableListOf<Uri>()
    var currentWallpaper:Int = -1

    fun addWallpaper(wpUri: Uri?): String {
        if (wpUri != null) {
            if (wallpaperMutableList.size < maxWallpapers) {
                wallpaperMutableList.add(wpUri)
                currentWallpaper = wallpaperMutableList.size - 1
                return "OK"
            } else {
                return "FULL"
            }
        }
        return "NULL"
    }

    fun removeWallpaper(): String{
        if (wallpaperMutableList.isEmpty()){
            return "EMPTY"
        }else{
            wallpaperMutableList.removeAt(currentWallpaper)
            currentWallpaper = wallpaperMutableList.size - 1
            return "OK"
        }
    }

    fun nextWallpaper(): Uri? {
        if ( ! wallpaperMutableList.isEmpty()) {
            currentWallpaper++
            if (currentWallpaper == wallpaperMutableList.size) {
                currentWallpaper = 0
            }
            return wallpaperMutableList[currentWallpaper]
        }else {
            return null
        }
    }

    fun prevWallpaper(): Uri?{
        if ( ! wallpaperMutableList.isEmpty()) {
            currentWallpaper--
            print("$currentWallpaper")
            if (currentWallpaper == -1) {
                currentWallpaper = wallpaperMutableList.size -1
            }
            return wallpaperMutableList[currentWallpaper]
        }else{
            return null
        }
    }

    fun currentWallpaper(): Uri? {
        if (!wallpaperMutableList.isEmpty()) {
            return wallpaperMutableList[currentWallpaper]
        } else {
            return null
        }
    }
}