package com.example.wallpaperapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperapp.data.database.entity.LikedImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedImage(image: LikedImageEntity)

    @Query("SELECT * FROM likedimageentity")
    fun getAllLikedImages(): Flow<List<LikedImageEntity>>

    @Query("DELETE FROM likedimageentity WHERE id = :id")
    fun deleteLikedImageById(id: String)

}