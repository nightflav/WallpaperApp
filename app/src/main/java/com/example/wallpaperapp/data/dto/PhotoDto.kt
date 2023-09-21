package com.example.wallpaperapp.data.dto

data class PhotoDto(
    val id: String,
    val uri: String,
) {
    companion object {
        val PhotoDtoHolder = PhotoDto(
            id = "holder",
            uri = "-1"
        )
    }
}
