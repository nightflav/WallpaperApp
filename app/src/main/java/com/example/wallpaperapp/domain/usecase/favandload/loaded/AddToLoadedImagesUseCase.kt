package com.example.wallpaperapp.domain.usecase.favandload.loaded

import com.example.wallpaperapp.domain.repository.ImagesRepository
import javax.inject.Inject

class AddToLoadedImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(id: String, path: String) {
        imagesRepository.addLoadedImage(id, path)
    }

}