package com.example.wallpaperapp.di

import android.content.Context
import com.example.wallpaperapp.di.modules.DataModule
import com.example.wallpaperapp.di.modules.DatabaseModule
import com.example.wallpaperapp.di.modules.NetworkModule
import com.example.wallpaperapp.di.qualifiers.DBContextQualifier
import com.example.wallpaperapp.di.subcomponents.categories.CategoriesSubcomponent
import com.example.wallpaperapp.di.subcomponents.favourite.FavouriteSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.BigImageSubcomponent
import com.example.wallpaperapp.di.subcomponents.images.ImagesSubcomponent
import com.example.wallpaperapp.di.subcomponents.loaded.LoadedSubcomponent
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [NetworkModule::class, DataModule::class, DatabaseModule::class]
)
interface AppComponent {

    val categoriesComponent: CategoriesSubcomponent.Factory

    val imagesComponent: ImagesSubcomponent.Factory

    val bigImageComponent: BigImageSubcomponent.Factory

    val favouriteSubcomponent: FavouriteSubcomponent.Factory

    val loadedSubcomponent: LoadedSubcomponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @DBContextQualifier
            @BindsInstance
            context: Context
        ): AppComponent
    }
}