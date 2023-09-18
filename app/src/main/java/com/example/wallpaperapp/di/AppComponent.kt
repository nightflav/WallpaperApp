package com.example.wallpaperapp.di

import com.example.wallpaperapp.di.modules.DataModule
import com.example.wallpaperapp.di.modules.NetworkModule
import com.example.wallpaperapp.di.subcomponents.categories.CategoriesSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.BigImageSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.ImagesSubcomponent
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, DataModule::class])
interface AppComponent {

    val categoriesComponent: CategoriesSubcomponent.Factory

    val imagesComponent: ImagesSubcomponent.Factory

    val bigImageComponent: BigImageSubcomponent.Factory

    interface Factory {
        fun create(): AppComponent
    }

}