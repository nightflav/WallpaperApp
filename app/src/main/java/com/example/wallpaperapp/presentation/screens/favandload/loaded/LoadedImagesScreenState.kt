package com.example.wallpaperapp.presentation.screens.favandload.loaded

import com.example.wallpaperapp.presentation.model.uimodel.LoadedImageItem

data class LoadedImagesScreenState(
    val images: List<LoadedImageItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
