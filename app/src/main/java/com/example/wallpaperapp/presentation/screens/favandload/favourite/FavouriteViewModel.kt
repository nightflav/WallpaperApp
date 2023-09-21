package com.example.wallpaperapp.presentation.screens.favandload.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.usecase.favandload.favourite.LoadFavouriteImagesUseCase
import com.example.wallpaperapp.domain.usecase.favandload.favourite.RemoveFromFavouritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val loadFavouriteImagesUseCase: LoadFavouriteImagesUseCase,
    private val removeFromFavourites: RemoveFromFavouritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteScreenState())
    val state = _state.asStateFlow()
    private val currState get() = state.value

    init {
        sendEvent(FavouriteScreenEvents.LoadFavourites)
    }

    sealed class FavouriteScreenEvents {
        data object LoadFavourites : FavouriteScreenEvents()
        data class RemoveFromFavourites(val id: String) : FavouriteScreenEvents()
    }

    fun sendEvent(e: FavouriteScreenEvents) {
        viewModelScope.launch(Dispatchers.IO) {
            when (e) {
                FavouriteScreenEvents.LoadFavourites -> loadFavourites()
                is FavouriteScreenEvents.RemoveFromFavourites -> removeFromFavourites(e.id)
            }
        }
    }

    private suspend fun loadFavourites() {
        loadFavouriteImagesUseCase().collect {
            _state.emit(
                currState.copy(
                    isLoading = false,
                    error = null,
                    images = it
                )
            )
        }
    }

    private suspend fun removeFromFavourites(id: String) {
        removeFromFavourites.invoke(id)
        loadFavourites()
    }
}