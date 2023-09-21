package com.example.wallpaperapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.wallpaperapp.data.database.ImagesDatabase
import com.example.wallpaperapp.di.ApplicationScope
import com.example.wallpaperapp.di.qualifiers.DBContextQualifier
import com.example.wallpaperapp.di.subcomponents.favourite.FavouriteSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.BigImageSubcomponent
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [FavouriteSubcomponent::class, BigImageSubcomponent::class]
)
class DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideImagesDatabase(
        @DBContextQualifier
        context: Context
    ): ImagesDatabase = Room.databaseBuilder(
        context,
        ImagesDatabase::class.java,
        "ImagesDB"
    ).build()

    @Provides
    fun provideLikedImagesDao(
        db: ImagesDatabase
    ) = db.likedImagesDao

    @Provides
    fun provideLoadedImagesDao(
        db: ImagesDatabase
    ) = db.loadedImagesDao

}