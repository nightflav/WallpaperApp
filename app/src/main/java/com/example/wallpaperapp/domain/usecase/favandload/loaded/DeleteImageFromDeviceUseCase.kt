package com.example.wallpaperapp.domain.usecase.favandload.loaded

import com.example.wallpaperapp.domain.repository.ImagesRepository
import javax.inject.Inject

class DeleteImageFromDeviceUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(id: String) {
        imagesRepository.deletePhotoFromDevice(id)
    }

}