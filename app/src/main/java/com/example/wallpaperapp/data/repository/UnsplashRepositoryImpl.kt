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

    private var repoCategoryCache: List<CategoryDto> = emptyList()
    private var repoPhotoCache: List<PhotoDto> = emptyList()

    override fun getCategories(
        page: Int, perPage: Int
    ): Flow<Resource<List<CategoryDto>>> = flow {
        emit(Resource.Loading(repoCategoryCache))
        try {
            val networkCategories = unsplashApi.getCategories(page, perPage)
            if (networkCategories.isSuccessful) {
                repoCategoryCache = networkCategories.body()!!.categoryDtoList
                emit(Resource.Success(repoCategoryCache))
            } else {
                emit(
                    Resource.Error(
                        Throwable(networkCategories.message()), repoCategoryCache
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Resource.Error(e, repoCategoryCache)
            )
        }
    }

    override fun getCategoryPhotos(
        categoryId: String, page: Int, perPage: Int
    ): Flow<Resource<List<PhotoDto>>> = flow {
        emit(Resource.Loading(repoPhotoCache))
        try {
            val networkPhotos = unsplashApi.getPhotosByCategory(categoryId, page, perPage)
            if (networkPhotos.isSuccessful) {
                repoPhotoCache = networkPhotos.body()!!.photoDtoList
                emit(Resource.Success(repoPhotoCache))
            } else {
                emit(
                    Resource.Error(
                        Throwable(message = networkPhotos.message()), repoPhotoCache
                    )
                )
            }
        } catch (e: Exception) {
            emit(Resource.Error(e, repoPhotoCache))
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