package com.example.wallpaperapp.data.network.model.photo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerialName("download")
    val download: String,
    @SerialName("download_location")
    val downloadLocation: String,
    @SerialName("html")
    val html: String,
    @SerialName("self")
    val self: String
)