package com.example.wallpaperapp.presentation.screens.favandload.loaded

import android.app.Application
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.di.qualifiers.LoadedAppQ
import com.example.wallpaperapp.domain.usecase.favandload.loaded.DeleteImageFromDeviceUseCase
import com.example.wallpaperapp.domain.usecase.favandload.loaded.LoadLoadedImagesUseCase
import com.example.wallpaperapp.presentation.model.uimodel.LoadedImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


class LoadedViewModel @Inject constructor(
    private val deleteImageFromDevice: DeleteImageFromDeviceUseCase,
    private val loadLoadedImages: LoadLoadedImagesUseCase,
    @LoadedAppQ private val application: Application
) : ViewModel() {

    private val _state = MutableStateFlow(LoadedImagesScreenState())
    val state = _state.asStateFlow()

    init {
        sendEvent(LoadedScreenStateEvent.LoadImages)
    }

    sealed class LoadedScreenStateEvent {
        data object LoadImages : LoadedScreenStateEvent()
        data class DeleteFromDevice(val id: String) : LoadedScreenStateEvent()
    }

    fun sendEvent(e: LoadedScreenStateEvent) {
        viewModelScope.launch {
            when (e) {
                is LoadedScreenStateEvent.DeleteFromDevice -> deleteImage(e.id)
                LoadedScreenStateEvent.LoadImages -> loadImages()
            }
        }
    }

    private suspend fun loadImages() {
        loadLoadedImages().collect { newState ->
            val images = newState.map { loadedImage ->
                val image = File(loadedImage.path)
                val bmOptions = BitmapFactory.Options()
                BitmapFactory.decodeFile(image.absolutePath, bmOptions)
            }
            _state.emit(
                state.value.copy(
                    isLoading = false,
                    error = null,
                    images = newState.mapIndexed { i, it ->
                        LoadedImageItem(
                            path = it.path,
                            id = it.id,
                            bitmap = images[i]
                        )
                    }
                )
            )
        }
    }

    private suspend fun deleteImage(id: String) {
        val path = state.value.images.first { it.id == id }.path
        deleteImageFromDevice(id)
        val fDelete = File(path)
        withContext(Dispatchers.IO) {
            if (fDelete.exists()) {
                if (fDelete.delete()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            application,
                            "File successfully deleted",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            application,
                            "File is not deleted",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}