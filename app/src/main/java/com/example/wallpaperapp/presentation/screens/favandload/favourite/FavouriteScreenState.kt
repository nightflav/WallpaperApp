package com.example.wallpaperapp.presentation.screens.favandload.favourite

import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem

data class FavouriteScreenState(
    val images: List<PhotoItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
