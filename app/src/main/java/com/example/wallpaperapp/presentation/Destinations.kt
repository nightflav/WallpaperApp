package com.example.wallpaperapp.presentation

sealed class Destinations(open val route: String) {
    data object CategoryScreen : Destinations(route = "category_screen")
    data object SelectedCategoryScreen : Destinations(route = "selected_category")
    data object SelectedImageScreen : Destinations(route = "selected_image")
    data object SettingsScreen : Destinations(route = "settings")
    data object FavouritesScreen : Destinations(route = "favourites")
    data object LoadedScreen : Destinations(route = "loaded_images")
}
