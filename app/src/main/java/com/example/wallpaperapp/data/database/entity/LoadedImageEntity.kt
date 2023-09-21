package com.example.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wallpaperapp.data.dto.PhotoDto

@Entity
data class LoadedImageEntity(
    @PrimaryKey
    val id: String,
    val path: String
) {
    val photoDto
        get() = PhotoDto(
            id = id,
            uri = path
        )
}
