package com.example.wallpaperapp.presentation.screens.categoryscreen

import com.example.wallpaperapp.presentation.model.uimodel.CategoryItem

data class CategoryScreenState(
    val categories: List<CategoryItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
