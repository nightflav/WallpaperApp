package com.example.wallpaperapp.di.subcomponents.images

import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesViewModel
import dagger.Subcomponent

@ImagesScope
@Subcomponent
interface ImagesSubcomponent {

    val imagesViewModel: ImagesViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): ImagesSubcomponent
    }

}