package com.example.wallpaperapp.domain.usecase.images

import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem
import com.example.wallpaperapp.domain.repository.ImagesRepository
import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.core.util.photoItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadImagesUseCase @Inject constructor(
    private val repo: ImagesRepository
) {

    operator fun invoke(id: String, page: Int, perPage: Int): Flow<Resource<List<PhotoItem>>> =
        repo.getCategoryPhotos(id, page, perPage).map {
            when (it) {
                is Resource.Error -> Resource.Error(it.error, it.prevState.photoItemList)
                is Resource.Loading -> Resource.Loading(it.prevState.photoItemList)
                is Resource.Success -> Resource.Success(it.newState.photoItemList)
            }
        }

}