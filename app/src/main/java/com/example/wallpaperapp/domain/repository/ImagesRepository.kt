package com.example.wallpaperapp.domain.repository

import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.data.dto.CategoryDto
import com.example.wallpaperapp.data.dto.PhotoDto
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    fun getCategories(page: Int = 1, perPage: Int = 20): Flow<Resource<List<CategoryDto>>>

    fun getCategoryPhotos(
        categoryId: String,
        page: Int = 1,
        perPage: Int = 20
    ): Flow<Resource<List<PhotoDto>>>

    fun getPhotoById(
        id: String,
    ): Flow<Resource<PhotoDto>>

    suspend fun addLikedImage(id: String)

    suspend fun addLoadedImage(id: String, path: String)

    fun getLikedImages(): Flow<List<PhotoDto>>

    fun getLoadedImages(): Flow<List<PhotoDto>>

    suspend fun removeLikedImage(id: String)

    suspend fun deletePhotoFromDevice(id: String)

}