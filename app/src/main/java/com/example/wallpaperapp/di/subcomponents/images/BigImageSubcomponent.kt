package com.example.wallpaperapp.di.subcomponents.images

import android.content.Context
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@BigImageScope
@Subcomponent
interface BigImageSubcomponent {

    val bigImageViewModel: BigImageViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): BigImageSubcomponent
    }
}