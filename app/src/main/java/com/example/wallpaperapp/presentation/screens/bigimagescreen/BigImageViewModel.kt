package com.example.wallpaperapp.presentation.screens.bigimagescreen

import android.app.Application
import android.app.WallpaperManager
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.core.util.cropBitmapFromCenterAndScreenSize
import com.example.wallpaperapp.core.util.saveImage
import com.example.wallpaperapp.di.qualifiers.BigImageAppQ
import com.example.wallpaperapp.di.qualifiers.ImageIdQualifier
import com.example.wallpaperapp.domain.usecase.bigimage.AddToFavouriteUseCase
import com.example.wallpaperapp.domain.usecase.bigimage.LoadImageUseCase
import com.example.wallpaperapp.domain.usecase.favandload.loaded.AddToLoadedImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BigImageViewModel @Inject constructor(
    @ImageIdQualifier private val imageId: String,
    private val loadImageUseCase: LoadImageUseCase,
    private val addToFavourite: AddToFavouriteUseCase,
    private val addToLoadImageUseCase: AddToLoadedImagesUseCase,
    @BigImageAppQ private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(BigImageScreenState())
    val state = _state.asStateFlow()

    init {
        sendEvent(ImageScreenEvents.LoadImage)
    }

    sealed class ImageScreenEvents {
        data object LoadImage : ImageScreenEvents()
        data class InstallAsWallpaper(val type: Int) : ImageScreenEvents()
        data object AddToFavourite : ImageScreenEvents()
        data object LoadToDevice : ImageScreenEvents()
    }

    fun sendEvent(e: ImageScreenEvents) {
        viewModelScope.launch {
            when (e) {
                is ImageScreenEvents.InstallAsWallpaper -> installImageAsWallpaper(e.type)
                is ImageScreenEvents.LoadImage -> loadImage()
                ImageScreenEvents.AddToFavourite -> addToFavourite()
                ImageScreenEvents.LoadToDevice -> loadToDevice()
            }
        }
    }

    private suspend fun loadToDevice() {
        val image = state.value.image
        withContext(Dispatchers.IO) {
            try {
                val path = image?.saveImage(image.hashCode().toString())
                addToLoadImageUseCase(
                    image.hashCode().toString(),
                    path!!
                )
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "Image successfully saved", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "Cannot save image", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private suspend fun addToFavourite() = addToFavourite(imageId)

    private fun installImageAsWallpaper(type: Int) {
        val image = state.value.image
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (image == null)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "Image is loading", Toast.LENGTH_LONG).show()
                    }
                else {
                    val wallpaperManager = WallpaperManager.getInstance(application)
                    wallpaperManager.setBitmap(image, null, true, type)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "DONE", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun loadImage(id: String = imageId) {
        viewModelScope.launch {
            loadImageUseCase(imageId = id).collect {
                when (it) {
                    is Resource.Error -> _state.emit(
                        state.value.copy(
                            error = it.error,
                            isLoading = false
                        )
                    )

                    is Resource.Loading -> _state.emit(
                        state.value.copy(
                            isLoading = true,
                            error = null
                        )
                    )

                    is Resource.Success -> {
                        val imageFuture = Glide.with(application)
                            .asBitmap()
                            .load(it.newState.uri)
                            .submit()
                        viewModelScope.launch(Dispatchers.IO) {
                            _state.emit(
                                state.value.copy(
                                    isLoading = false,
                                    image = imageFuture.get()
                                        .cropBitmapFromCenterAndScreenSize(application)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
