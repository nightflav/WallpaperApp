package com.example.wallpaperapp.di.subcomponents.images

import com.example.wallpaperapp.di.qualifiers.CategoryIdQualifier
import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@ImagesScope
@Subcomponent
interface ImagesSubcomponent {

    @ImagesScope
    val imagesViewModel: ImagesViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @CategoryIdQualifier
            @BindsInstance
            categoryId: String,
        ): ImagesSubcomponent
    }

}