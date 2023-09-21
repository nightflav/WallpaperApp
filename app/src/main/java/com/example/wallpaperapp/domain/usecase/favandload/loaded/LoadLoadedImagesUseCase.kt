package com.example.wallpaperapp.domain.usecase.favandload.loaded

import com.example.wallpaperapp.core.util.loadedImageItem
import com.example.wallpaperapp.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadLoadedImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    operator fun invoke() = imagesRepository
        .getLoadedImages().map {
            it.map { image -> image.loadedImageItem }
        }

}