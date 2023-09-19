package com.example.wallpaperapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel
import com.example.wallpaperapp.presentation.screens.categoryscreen.CategoryScreenViewModel
import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryScreenViewModel::class)
    fun categoryScreenViewModel(viewModel: CategoryScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    fun imagesViewModel(viewModel: ImagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BigImageViewModel::class)
    fun bigImageViewModel(viewModel: BigImageViewModel): ViewModel
}