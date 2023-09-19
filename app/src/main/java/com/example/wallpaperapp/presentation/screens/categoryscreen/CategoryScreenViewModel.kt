package com.example.wallpaperapp.presentation.screens.categoryscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.usecase.category.LoadCategoriesUseCase
import com.example.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryScreenViewModel @Inject constructor(
    private val loadCategoriesUseCase: LoadCategoriesUseCase
) : ViewModel() {

    sealed class CategoryScreenEvent {
        data object LoadCategories : CategoryScreenEvent()
    }

    private val _state = MutableStateFlow(CategoryScreenState())
    val state: StateFlow<CategoryScreenState> = _state.asStateFlow()

    init {
        Log.d("TAGTAG", "Category VM Created")
        sendEvent(CategoryScreenEvent.LoadCategories)
    }


    fun sendEvent(e: CategoryScreenEvent) {
        viewModelScope.launch {
            when (e) {
                CategoryScreenEvent.LoadCategories -> loadCategories(1)
            }
        }
    }

    private suspend fun loadCategories(page: Int) {
        loadCategoriesUseCase.invoke(page).collect {
            when (it) {
                is Resource.Error -> showErrorScreen(it.error)
                is Resource.Success -> {
                    _state.emit(
                        state.value.copy(
                            isLoading = false,
                            error = null,
                            categories = state.value.categories.toMutableList().plus(it.value!!)
                        )
                    )
                }

                is Resource.Loading -> showLoadingScreen()
            }
        }
    }

    private suspend fun showErrorScreen(e: Throwable) {
        _state.emit(
            state.value.copy(
                isLoading = false,
                error = e
            )
        )
    }

    private suspend fun showLoadingScreen() {
        _state.emit(
            state.value.copy(
                isLoading = true,
                error = null
            )
        )
    }
}