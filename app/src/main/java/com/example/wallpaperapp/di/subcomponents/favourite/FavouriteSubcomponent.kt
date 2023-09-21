package com.example.wallpaperapp.di.subcomponents.favourite

import com.example.wallpaperapp.presentation.screens.favandload.favourite.FavouriteViewModel
import dagger.Subcomponent

@FavouriteScope
@Subcomponent
interface FavouriteSubcomponent {

    @FavouriteScope
    val favouriteViewModel: FavouriteViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavouriteSubcomponent
    }

}