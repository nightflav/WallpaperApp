package com.example.wallpaperapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallpaperapp.data.database.dao.LikedImagesDao
import com.example.wallpaperapp.data.database.dao.LoadedImagesDao
import com.example.wallpaperapp.data.database.entity.LikedImageEntity
import com.example.wallpaperapp.data.database.entity.LoadedImageEntity

@Database(
    entities = [LikedImageEntity::class, LoadedImageEntity::class],
    version = 1
)
abstract class ImagesDatabase : RoomDatabase() {

    abstract val likedImagesDao: LikedImagesDao

    abstract val loadedImagesDao: LoadedImagesDao

}