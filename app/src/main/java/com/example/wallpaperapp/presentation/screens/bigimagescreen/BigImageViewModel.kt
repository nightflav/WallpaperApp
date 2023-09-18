package com.example.wallpaperapp.presentation.screens.bigimagescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.usecase.bigimage.InstallImageAsWallpaper
import com.example.wallpaperapp.domain.usecase.bigimage.LoadImage
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BigImageViewModel @Inject constructor(
    private val installImageAsWallpaper: InstallImageAsWallpaper,
    private val loadImage: LoadImage
) : ViewModel() {

    private val _state = MutableStateFlow(BigImageScreenState())
    val state = _state.asStateFlow()

    sealed class ImageScreenEvents {
        data class LoadImage(val id: String) : ImageScreenEvents()
        data class InstallAsWallpaper(val type: Int) : ImageScreenEvents()
    }

    fun sendEvent(e: ImageScreenEvents) {
        viewModelScope.launch {
            when (e) {
                is ImageScreenEvents.InstallAsWallpaper -> installImageAsWallpaper(e.type)
                is ImageScreenEvents.LoadImage -> loadImage(e.id)
            }
        }
    }

    private fun installImageAsWallpaper(type: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            installImageAsWallpaper.invoke(state.value.image, type)
        }
    }

    private suspend fun loadImage(id: String) {
        viewModelScope.launch {
            loadImage.invoke(imageId = id).collect {
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

                    is Resource.Success -> _state.emit(
                        state.value.copy(
                            isLoading = false,
                            image = it.newState,
                            error = null
                        )
                    )
                }
            }
        }
    }
}