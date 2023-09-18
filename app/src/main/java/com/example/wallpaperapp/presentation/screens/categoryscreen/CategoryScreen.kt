package com.example.wallpaperapp.presentation.screens.categoryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wallpaperapp.data.uimodel.CategoryItem
import com.example.wallpaperapp.presentation.Destinations
import com.example.wallpaperapp.presentation.screens.ErrorScreen
import com.example.wallpaperapp.presentation.screens.LoadingScreen
import com.example.wallpaperapp.util.fadingEdge

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryScreenViewModel = viewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(e = state.error!!) {
            viewModel.sendEvent(CategoryScreenViewModel.CategoryScreenEvent.LoadCategories)
        }

        else -> SuccessfulScreen(
            modifier = modifier,
            onCategorySelectedListener = {
                if (navController.currentDestination?.route != "${Destinations.SelectedCategoryScreen.route}/$it")
                    navController.navigate(
                        route = "${Destinations.SelectedCategoryScreen.route}/$it"
                    )
            },
            onNavigateToSettingsScreen = {
                if (navController.currentDestination?.route != Destinations.SettingsScreen.route)
                    navController.navigate(
                        route = Destinations.SettingsScreen.route
                    )
            },
            categories = state.categories
        )
    }
}

@Composable
fun SuccessfulScreen(
    modifier: Modifier = Modifier,
    onCategorySelectedListener: (String) -> Unit,
    onNavigateToSettingsScreen: () -> Unit,
    categories: List<CategoryItem>
) {
    val topFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.02f to MaterialTheme.colorScheme.surface,
    )
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Select a category",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(15)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
            IconButton(onClick = onNavigateToSettingsScreen) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        CategoriesGrid(
            modifier = modifier.fadingEdge(topFade),
            categories = categories,
            onCategorySelectedListener = onCategorySelectedListener,
        )
    }
}

@Composable
private fun CategoriesGrid(
    modifier: Modifier = Modifier,
    categories: List<CategoryItem>,
    onCategorySelectedListener: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            //FavouritesAndLoadedImages()
        }
        itemsIndexed(
            items = categories,
            key = { index, _ -> categories[index].id },
            contentType = { _, item -> item::class }
        ) { _, item ->
            CategoryCard(
                category = item,
                onCategoryClick = {
                    onCategorySelectedListener(item.id)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoriesGrid(
        Modifier,
        listOf(
            CategoryItem(
                id = "1",
                coverUrl = "",
                description = "Long long description"
            ),
            CategoryItem(
                id = "2",
                coverUrl = "",
                description = "Long long description"
            ),
            CategoryItem(
                id = "3",
                coverUrl = "",
                description = "Long long description"
            ),
            CategoryItem(
                id = "4",
                coverUrl = "",
                description = "Long long description"
            ),
            CategoryItem(
                id = "5",
                coverUrl = "",
                description = "Long long description"
            )
        ),
    )
    {}
}