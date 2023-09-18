package com.example.wallpaperapp.presentation.screens.imagesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.usecase.images.LoadImagesUseCase
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val loadPhotosUseCase: LoadImagesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ImagesScreenState())
    val state = _state.asStateFlow()

    sealed class ImagesScreenEvents {
        data class LoadImages(val categoryId: String) : ImagesScreenEvents()
    }

    fun sendEvent(e: ImagesScreenEvents) {
        viewModelScope.launch {
            when (e) {
                is ImagesScreenEvents.LoadImages -> loadImages(e.categoryId)
            }
        }
    }

    private suspend fun loadImages(id: String) {
        loadPhotosUseCase(id, 1, 15).collect {
            when (it) {
                is Resource.Error -> showErrorScreen(it.error)
                is Resource.Loading -> showLoadingScreen()
                is Resource.Success -> _state.emit(
                    state.value.copy(
                        isLoading = false,
                        error = null,
                        images = it.newState
                    )
                )
            }
        }
    }

    private suspend fun showLoadingScreen() {
        _state.emit(
            state.value.copy(
                isLoading = true
            )
        )
    }

    private suspend fun showErrorScreen(e: Throwable) {
        _state.emit(
            state.value.copy(
                error = e,
                isLoading = false
            )
        )
    }
}