package com.example.wallpaperapp.data.network.model.photo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPhotoItem(
    @SerialName("id")
    val id: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("urls")
    val urls: Urls
)