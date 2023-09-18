package com.example.wallpaperapp.domain.usecase.category

import com.example.wallpaperapp.data.uimodel.CategoryItem
import com.example.wallpaperapp.domain.repository.UnsplashRepository
import com.example.wallpaperapp.util.Resource
import com.example.wallpaperapp.util.categoryItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val repo: UnsplashRepository
) {

    operator fun invoke(page: Int): Flow<Resource<List<CategoryItem>>> = repo.getCategories(page).map {
        when (it) {
            is Resource.Error -> Resource.Error(it.error, it.prevState.categoryItemList)
            is Resource.Loading -> Resource.Loading(it.prevState.categoryItemList)
            is Resource.Success -> Resource.Success(it.newState.categoryItemList)
        }
    }

}