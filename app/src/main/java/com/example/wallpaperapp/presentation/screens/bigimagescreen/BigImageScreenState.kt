package com.example.wallpaperapp.presentation.screens.bigimagescreen

import android.graphics.Bitmap

data class BigImageScreenState(
    val image: Bitmap? = null,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
