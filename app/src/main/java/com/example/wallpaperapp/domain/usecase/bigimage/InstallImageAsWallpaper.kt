package com.example.wallpaperapp.domain.usecase.bigimage

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InstallImageAsWallpaper @Inject constructor(
    private val context: Context,
) {
    suspend operator fun invoke(image: Bitmap?, lockOrBackGround: Int) {
        try {
            if (image == null)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Image is loading", Toast.LENGTH_LONG).show()
                }
            else {
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(image, null, true, lockOrBackGround)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "DONE", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Throwable) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

