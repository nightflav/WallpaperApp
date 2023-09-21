package com.example.wallpaperapp.presentation.screens.imagesscreen

import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem

data class ImagesScreenState(
    val images: List<PhotoItem> = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = true
)
