package com.example.wallpaperapp.domain.usecase.favandload.favourite

import com.example.wallpaperapp.domain.repository.ImagesRepository
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(id: String) = imagesRepository.removeLikedImage(id)

}