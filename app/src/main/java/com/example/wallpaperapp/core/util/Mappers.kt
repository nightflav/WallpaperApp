package com.example.wallpaperapp.core.util

import com.example.wallpaperapp.data.dto.CategoryDto
import com.example.wallpaperapp.data.dto.PhotoDto
import com.example.wallpaperapp.data.network.model.photo.NetworkPhotoItem
import com.example.wallpaperapp.data.network.model.topic.TopicNetworkItem
import com.example.wallpaperapp.presentation.model.uimodel.CategoryItem
import com.example.wallpaperapp.presentation.model.uimodel.LoadedImageItem
import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem

val CategoryDto.categoryItem
    get() = CategoryItem(
        id = id,
        coverUrl = coverUrl,
        description = description
    )

val PhotoDto.photoItem
    get() = PhotoItem(
        id = id,
        uri = uri
    )

val PhotoDto.loadedImageItem
    get() = LoadedImageItem(
        id = id,
        path = uri
    )

val List<CategoryDto>?.categoryItemList get() = this?.map { it.categoryItem } ?: emptyList()

val List<PhotoDto>?.photoItemList get() = this?.map { it.photoItem } ?: emptyList()

val List<TopicNetworkItem>.categoryDtoList
    get() = map {
        CategoryDto(
            id = it.id,
            coverUrl = it.coverPhoto.urls.smallS3,
            description = it.description
        )
    }

val List<NetworkPhotoItem>.photoDtoList
    get() = map {
        it.lowResPhotoDto
    }

val NetworkPhotoItem.regularPhotoDto
    get() = PhotoDto(
        id = id,
        uri = urls.full
    )

val NetworkPhotoItem.lowResPhotoDto get() = PhotoDto(
    id = id,
    uri = urls.small
)