package com.example.wallpaperapp.domain.usecase.category

import com.example.wallpaperapp.core.util.Resource
import com.example.wallpaperapp.core.util.categoryItemList
import com.example.wallpaperapp.presentation.model.uimodel.CategoryItem
import com.example.wallpaperapp.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val repo: ImagesRepository
) {

    operator fun invoke(page: Int): Flow<Resource<List<CategoryItem>>> = repo.getCategories(page).map {
        when (it) {
            is Resource.Error -> Resource.Error(it.error, it.prevState.categoryItemList)
            is Resource.Loading -> Resource.Loading(it.prevState.categoryItemList)
            is Resource.Success -> Resource.Success(it.newState.categoryItemList)
        }
    }

}