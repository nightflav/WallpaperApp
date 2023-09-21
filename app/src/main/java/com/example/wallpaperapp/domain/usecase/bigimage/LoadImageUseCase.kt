package com.example.wallpaperapp.domain.usecase.bigimage

import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.core.util.photoItem
import com.example.wallpaperapp.domain.repository.ImagesRepository
import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repo: ImagesRepository,
) {
    suspend operator fun invoke(
        imageId: String
    ): Flow<Resource<PhotoItem>> = flow {
        repo.getPhotoById(imageId).collect {
            when (it) {
                is Resource.Error -> emit(Resource.Error(it.error, null))
                is Resource.Loading -> emit(Resource.Loading(null))
                is Resource.Success -> {
                    emit(Resource.Success(it.newState.photoItem))
                }
            }
        }
    }
}