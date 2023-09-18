package com.example.wallpaperapp.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wallpaperapp.di.AppComponent
import com.example.wallpaperapp.presentation.Destinations
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageScreen
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel
import com.example.wallpaperapp.presentation.screens.categoryscreen.CategoryScreen
import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesScreen
import com.example.wallpaperapp.presentation.screens.imagesscreen.ImagesViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appComponent: AppComponent,
    context: Context,
    dataStore: DataStore<Preferences>,
    currTheme: Boolean
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Destinations.CategoryScreen.route
    ) {
        composable(route = Destinations.CategoryScreen.route) {
            val viewModel = appComponent.categoriesComponent.create().categoriesViewModel
            CategoryScreen(
                viewModel = viewModel,
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
            val viewModel = appComponent.imagesComponent.create().imagesViewModel
            val categoryId = it.arguments?.getString("id")!!
            viewModel.sendEvent(ImagesViewModel.ImagesScreenEvents.LoadImages(categoryId))
            ImagesScreen(
                viewModel = viewModel,
                categoryId = categoryId,
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
            val viewModel = appComponent.bigImageComponent.create(context).bigImageViewModel
            val imageId = it.arguments?.getString("id")!!
            viewModel.sendEvent(BigImageViewModel.ImageScreenEvents.LoadImage(imageId))
            BigImageScreen(
                imageId = imageId,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Destinations.SettingsScreen.route,
        ) {
            SettingScreen(dataStore, currTheme)
        }
    }
}