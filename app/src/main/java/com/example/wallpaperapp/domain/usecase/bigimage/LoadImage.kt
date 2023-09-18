package com.example.wallpaperapp.domain.usecase.bigimage

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.wallpaperapp.domain.repository.UnsplashRepository
import com.example.wallpaperapp.util.Resource
import com.example.wallpaperapp.util.cropBitmapFromCenterAndScreenSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadImage @Inject constructor(
    private val repo: UnsplashRepository,
    private val context: Context
) {
    suspend operator fun invoke(
        imageId: String
    ): Flow<Resource<Bitmap>> = flow {
        repo.getPhotoById(imageId).collect {
            when (it) {
                is Resource.Error -> emit(Resource.Error(it.error, null))
                is Resource.Loading -> emit(Resource.Loading(null))
                is Resource.Success -> {
                    val imageFuture = Glide.with(context)
                        .asBitmap()
                        .load(it.value!!.url)
                        .submit()
                    emit(Resource.Success(withContext(Dispatchers.IO) {
                        imageFuture.get().cropBitmapFromCenterAndScreenSize(context)!!
                    }))
                }
            }
        }
    }
}