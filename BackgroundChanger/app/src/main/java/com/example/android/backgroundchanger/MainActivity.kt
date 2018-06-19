package com.example.android.backgroundchanger

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.support.v4.content.res.TypedArrayUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
class MainActivity : AppCompatActivity() {

    private val numberOfImages = 1
    val myWpManager = WpManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        this.browserButton.setOnClickListener{
            this.selectImageInAlbum()
        }

        this.applyButton.setOnClickListener{
            val myImageViewDrawable = this.myFutureWallpaper.drawable
            val bitmapDrawable = myImageViewDrawable as BitmapDrawable
            val myFutureWallpaper = bitmapDrawable.bitmap
            try {
                val myWallpaperManager = WallpaperManager.getInstance(this.applicationContext)
                myWallpaperManager.setBitmap(myFutureWallpaper)
                Toast.makeText(this, "Wallpaper changed successfully", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                Toast.makeText(this, "Wallpaper not changed :(", Toast.LENGTH_LONG).show()
            }
        }

        this.deleteButton.setOnClickListener{
            val ans = myWpManager.removeWallpaper()
            if(ans == "OK"){
                if (myWpManager.currentWallpaper() != null) {
                    manageImageFromUri(myWpManager.nextWallpaper())
                }else{
                    this.myFutureWallpaper.visibility = View.INVISIBLE
                }
            }else if (ans == "EMPTY"){
                Toast.makeText(this, "List empty", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "weird: $ans", Toast.LENGTH_LONG).show()
            }
        }

        this.nextWallpaper.setOnClickListener{
            val ans = myWpManager.nextWallpaper()
            manageImageFromUri(ans)
        }

        this.prevWallpaper.setOnClickListener{
            val ans = myWpManager.prevWallpaper()
            manageImageFromUri(ans)
        }

    }
    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(this.packageManager) != null) {
            this.startActivityForResult(intent, this.numberOfImages)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode) {
                this.numberOfImages -> {this.manageImageFromUri(data?.data)
                                        myWpManager.addWallpaper(data?.data)}
            }
        }
    }

    private fun manageImageFromUri(imageUri: Uri?){
        var imageSelected: Bitmap? = null

        try {
            imageSelected = MediaStore.Images.Media.getBitmap(
                    this.contentResolver, imageUri)

        } catch (e: Exception) {
            // Manage exception ...
        }

        if (imageSelected != null) {
            this.myFutureWallpaper.visibility = View.VISIBLE
            this.myFutureWallpaper.setImageBitmap(imageSelected)
        }
    }
}


