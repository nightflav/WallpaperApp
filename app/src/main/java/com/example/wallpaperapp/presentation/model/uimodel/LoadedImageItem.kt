package com.example.wallpaperapp.presentation.model.uimodel

import android.graphics.Bitmap

data class LoadedImageItem(
    val path: String,
    val id: String,
    val bitmap: Bitmap? = null
)
