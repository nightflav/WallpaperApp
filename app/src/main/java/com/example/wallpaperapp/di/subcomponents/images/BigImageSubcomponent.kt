package com.example.wallpaperapp.di.subcomponents.images

import android.app.Application
import com.example.wallpaperapp.di.qualifiers.BigImageAppQ
import com.example.wallpaperapp.di.qualifiers.ImageIdQualifier
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@BigImageScope
@Subcomponent
interface BigImageSubcomponent {

    @BigImageScope
    val bigImageViewModel: BigImageViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @BigImageAppQ
            application: Application,
            @BindsInstance
            @ImageIdQualifier
            imageId: String
        ): BigImageSubcomponent
    }
}