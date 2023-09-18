package com.example.wallpaperapp.domain.usecase.images

import android.util.Log
import com.example.wallpaperapp.data.uimodel.PhotoItem
import com.example.wallpaperapp.domain.repository.UnsplashRepository
import com.example.wallpaperapp.util.Resource
import com.example.wallpaperapp.util.photoItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadImagesUseCase @Inject constructor(
    private val repo: UnsplashRepository
) {

    operator fun invoke(id: String, page: Int, perPage: Int): Flow<Resource<List<PhotoItem>>> =
        repo.getCategoryPhotos(id, page, perPage).map {
            Log.d("TAGTAG", "$it")
            when (it) {
                is Resource.Error -> Resource.Error(it.error, it.prevState.photoItemList)
                is Resource.Loading -> Resource.Loading(it.prevState.photoItemList)
                is Resource.Success -> Resource.Success(it.newState.photoItemList)
            }
        }

}