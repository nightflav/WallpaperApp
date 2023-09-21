package com.example.wallpaperapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaperapp.data.database.entity.LoadedImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoadedImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoadedImage(image: LoadedImageEntity)

    @Query("SELECT * FROM loadedimageentity")
    fun getLoadedImages(): Flow<List<LoadedImageEntity>>

    @Query("DELETE FROM loadedimageentity WHERE id = :id")
    suspend fun deleteLoadedImageById(id: String)

}