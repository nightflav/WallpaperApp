package com.example.wallpaperapp.domain.repository

import com.example.wallpaperapp.data.dto.CategoryDto
import com.example.wallpaperapp.data.dto.PhotoDto
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {

    fun getCategories(page: Int = 1, perPage: Int = 20): Flow<Resource<List<CategoryDto>>>

    fun getCategoryPhotos(
        categoryId: String,
        page: Int = 1,
        perPage: Int = 20
    ): Flow<Resource<List<PhotoDto>>>

    fun getPhotoById(
        id: String,
    ): Flow<Resource<PhotoDto>>

}