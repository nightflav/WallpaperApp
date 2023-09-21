package com.example.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedImageEntity(
    @PrimaryKey
    val id: String,
)
