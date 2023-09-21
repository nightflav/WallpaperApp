package com.example.wallpaperapp.di.subcomponents.loaded

import android.app.Application
import com.example.wallpaperapp.di.qualifiers.LoadedAppQ
import com.example.wallpaperapp.presentation.screens.favandload.loaded.LoadedViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@LoadedScope
@Subcomponent
interface LoadedSubcomponent {

    @LoadedScope
    val loadedViewModel: LoadedViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @LoadedAppQ
            application: Application
        ): LoadedSubcomponent
    }

}