package com.example.wallpaperapp.di.modules

import com.example.wallpaperapp.data.repository.ImagesRepositoryImpl
import com.example.wallpaperapp.di.subcomponents.categories.CategoriesSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.BigImageSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.ImagesSubcomponent
import com.example.wallpaperapp.domain.repository.ImagesRepository
import dagger.Binds
import dagger.Module

@Module(
    subcomponents =
    [BigImageSubcomponent::class, ImagesSubcomponent::class, CategoriesSubcomponent::class]
)
interface DataModule {

    @Binds
    fun bindUnsplashRepository(repo: ImagesRepositoryImpl): ImagesRepository

}