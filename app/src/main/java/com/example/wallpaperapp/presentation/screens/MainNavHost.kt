package com.example.wallpaperapp.presentation.screens

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wallpaperapp.di.AppComponent
import com.example.wallpaperapp.presentation.Destinations
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageScreen
import com.example.wallpaperapp.presentation.screens.categoryscreen.CategoryScreen
import com.example.wallpaperapp.presentation.screens.favandload.favourite.FavouriteScreen
import com.example.wallpaperapp.presentation.screens.favandload.loaded.LoadedScreen
import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appComponent: AppComponent,
    application: Application,
    dataStore: DataStore<Preferences>,
    currTheme: Boolean,
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Destinations.CategoryScreen.route
    ) {

        composable(route = Destinations.CategoryScreen.route) {
            val categoriesComponent = appComponent.categoriesComponent.create()
            CategoryScreen(
                viewModel = daggerViewModel { categoriesComponent.categoriesViewModel },
                navController = navController
            )
        }

        composable(
            route = Destinations.SelectedCategoryScreen.route + """/{id}""",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val categoryId = it.arguments?.getString("id")!!
            val imagesComponent = appComponent.imagesComponent.create(categoryId)
            ImagesScreen(
                viewModel = daggerViewModel {
                    imagesComponent.imagesViewModel
                },
                navController = navController
            )
        }

        composable(
            route = Destinations.SelectedImageScreen.route + """/{id}""",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            val imageId = it.arguments?.getString("id")!!
            val bigImageComponent = appComponent.bigImageComponent.create(application, imageId)

            BigImageScreen(
                viewModel = daggerViewModel {
                    bigImageComponent.bigImageViewModel
                },
                navController = navController
            )
        }

        composable(
            route = Destinations.SettingsScreen.route,
        ) {
            SettingScreen(dataStore, currTheme)
        }

        composable(
            route = Destinations.FavouritesScreen.route
        ) {
            val favComponent = appComponent.favouriteSubcomponent.create()
            FavouriteScreen(
                viewModel = daggerViewModel {
                    favComponent.favouriteViewModel
                }
            )
        }

        composable(
            route = Destinations.LoadedScreen.route
        ) {
            val loadedComponent = appComponent.loadedSubcomponent.create(application)
            LoadedScreen(
                viewModel = daggerViewModel {
                    loadedComponent.loadedViewModel
                }
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
@Composable
private inline fun <reified T : ViewModel> daggerViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T =
    viewModel(
        modelClass = T::class.java,
        key = key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInstanceCreator() as T
            }
        }
    )