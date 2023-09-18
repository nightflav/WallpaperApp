package com.example.wallpaperapp.data.network.model.topic


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicNetworkItem(
    @SerialName("cover_photo")
    val coverPhoto: CoverPhoto,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("title")
    val title: String,
    @SerialName("total_photos")
    val totalPhotos: Int
)