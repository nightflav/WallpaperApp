package com.example.wallpaperapp.di.modules

import com.example.wallpaperapp.presentation.screens.categoryscreen.CategoryScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryScreenViewModel::class)
    fun categoryScreenViewModel(viewModel: CategoryScreenViewModel): CategoryScreenViewModel

}