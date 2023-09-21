package com.example.wallpaperapp.domain.usecase.favandload.favourite

import com.example.wallpaperapp.core.util.photoItem
import com.example.wallpaperapp.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadFavouriteImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    operator fun invoke() = imagesRepository.getLikedImages().map {
        it.map { image -> image.photoItem }
    }

}