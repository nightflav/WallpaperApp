package com.example.wallpaperapp.data.repository

import com.example.wallpaperapp.data.dto.CategoryDto
import com.example.wallpaperapp.data.dto.PhotoDto
import com.example.wallpaperapp.data.network.UnsplashApi
import com.example.wallpaperapp.domain.repository.UnsplashRepository
import com.example.wallpaperapp.util.Resource
import com.example.wallpaperapp.util.categoryDtoList
import com.example.wallpaperapp.util.photoDtoList
import com.example.wallpaperapp.util.regularPhotoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashApi: UnsplashApi
) : UnsplashRepository {

    override fun getCategories(
        page: Int, perPage: Int
    ): Flow<Resource<List<CategoryDto>>> = flow {
        emit(Resource.Loading(null))
        try {
            val networkCategories = unsplashApi.getCategories(page, perPage)
            if (networkCategories.isSuccessful) {
                emit(Resource.Success(networkCategories.body()!!.categoryDtoList))
            } else {
                emit(
                    Resource.Error(
                        Throwable(networkCategories.message()), null
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Resource.Error(e, null)
            )
        }
    }

    override fun getCategoryPhotos(
        categoryId: String, page: Int, perPage: Int
    ): Flow<Resource<List<PhotoDto>>> = flow {
        emit(Resource.Loading(null))
        try {
            val networkPhotos = unsplashApi.getPhotosByCategory(categoryId, page, perPage)
            if (networkPhotos.isSuccessful) {
                emit(Resource.Success(networkPhotos.body()!!.photoDtoList))
            } else {
                emit(
                    Resource.Error(
                        Throwable(message = networkPhotos.message()), null
                    )
                )
            }
        } catch (e: Exception) {
            emit(Resource.Error(e, null))
        }
    }

    override fun getPhotoById(id: String): Flow<Resource<PhotoDto>> = flow {
        emit(Resource.Loading(null))
        try {
            val networkImage = unsplashApi.getPhotoById(id)
            if (networkImage.isSuccessful) {
                emit(Resource.Success(networkImage.body()!!.regularPhotoDto))
            } else {
                emit(Resource.Error(Throwable(message = networkImage.message()), null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e, null))
        }
    }
}