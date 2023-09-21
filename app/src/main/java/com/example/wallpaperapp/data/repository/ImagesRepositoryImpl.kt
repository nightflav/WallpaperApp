package com.example.wallpaperapp.data.repository

import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.core.util.categoryDtoList
import com.example.wallpaperapp.core.util.lowResPhotoDto
import com.example.wallpaperapp.core.util.photoDtoList
import com.example.wallpaperapp.core.util.regularPhotoDto
import com.example.wallpaperapp.data.database.dao.LikedImagesDao
import com.example.wallpaperapp.data.database.dao.LoadedImagesDao
import com.example.wallpaperapp.data.database.entity.LikedImageEntity
import com.example.wallpaperapp.data.database.entity.LoadedImageEntity
import com.example.wallpaperapp.data.dto.CategoryDto
import com.example.wallpaperapp.data.dto.PhotoDto
import com.example.wallpaperapp.data.network.UnsplashApi
import com.example.wallpaperapp.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val likedImagesDao: LikedImagesDao,
    private val loadedImagesDao: LoadedImagesDao
) : ImagesRepository {

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

    override suspend fun addLikedImage(id: String) {
        likedImagesDao.insertLikedImage(LikedImageEntity(id))
    }

    override suspend fun addLoadedImage(id: String, path: String) {
        loadedImagesDao.insertLoadedImage(LoadedImageEntity(id, path))
    }

    override fun getLikedImages(): Flow<List<PhotoDto>> = likedImagesDao.getAllLikedImages().map {
        it.map { image ->
            try {
                unsplashApi.getPhotoById(image.id).body()!!.lowResPhotoDto
            } catch (e: Exception) {
                PhotoDto.PhotoDtoHolder
            }
        }
    }

    override fun getLoadedImages(): Flow<List<PhotoDto>> = loadedImagesDao.getLoadedImages().map {
        it.map { image -> image.photoDto }
    }

    override suspend fun removeLikedImage(id: String) =
        likedImagesDao.deleteLikedImageById(id)

    override suspend fun deletePhotoFromDevice(id: String) =
        loadedImagesDao.deleteLoadedImageById(id)

}