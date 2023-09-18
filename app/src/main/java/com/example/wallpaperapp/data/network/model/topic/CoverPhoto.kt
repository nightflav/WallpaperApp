package com.example.wallpaperapp.data.network.model.topic


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverPhoto(
    @SerialName("id")
    val id: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("urls")
    val urls: Urls
)