package com.example.wallpaperapp.di.subcomponents.categories

import com.example.wallpaperapp.presentation.screens.categoryscreen.CategoryScreenViewModel
import dagger.Subcomponent

@CategoryScope
@Subcomponent
interface CategoriesSubcomponent {

    @CategoryScope
    val categoriesViewModel: CategoryScreenViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoriesSubcomponent
    }
}