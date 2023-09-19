package com.example.wallpaperapp.presentation.screens.imagesscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wallpaperapp.data.uimodel.PhotoItem
import com.example.wallpaperapp.presentation.Destinations
import com.example.wallpaperapp.presentation.screens.ErrorScreen
import com.example.wallpaperapp.presentation.screens.LoadingScreen
import com.example.wallpaperapp.util.fadingEdge

@Composable
fun ImagesScreen(
    modifier: Modifier = Modifier,
    viewModel: ImagesViewModel,
    navController: NavHostController
) {
    val topFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.01f to MaterialTheme.colorScheme.surface,
    )
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(e = state.error!!) {
            viewModel.sendEvent(ImagesViewModel.ImagesScreenEvents.LoadImages)
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select an image",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(15)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
        Box(
            modifier = Modifier.fadingEdge(topFade)
        ) {
            ImagesGrid(
                images = state.images,
                onImageClickListener = {
                    navController.navigate(
                        route = Destinations.SelectedImageScreen.route + "/$it"
                    )
                }
            )
        }
    }
}

@Composable
fun ImagesGrid(
    images: List<PhotoItem>,
    onImageClickListener: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 4.dp,
    ) {
        itemsIndexed(
            items = images,
            key = { _, item -> item.toString() },
            contentType = { _, item -> item::class }
        ) { _, item ->
            ImageCard(imageModel = item) {
                onImageClickListener(item.id)
            }
        }
    }
}