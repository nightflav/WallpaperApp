package com.example.wallpaperapp.domain.usecase.bigimage

import com.example.wallpaperapp.domain.repository.ImagesRepository
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    suspend operator fun invoke(id: String) = imagesRepository.addLikedImage(id)

}