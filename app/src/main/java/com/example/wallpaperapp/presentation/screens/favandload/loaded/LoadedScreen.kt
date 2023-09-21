package com.example.wallpaperapp.presentation.screens.favandload.loaded

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.wallpaperapp.presentation.model.uimodel.LoadedImageItem
import com.example.wallpaperapp.presentation.screens.ErrorScreen
import com.example.wallpaperapp.presentation.screens.LoadingScreen

@Composable
fun LoadedScreen(
    viewModel: LoadedViewModel
) {
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(e = state.error!!) {
            viewModel.sendEvent(LoadedViewModel.LoadedScreenStateEvent.LoadImages)
        }

        else -> SuccessfulLoadedScreen(images = state.images) {
            viewModel.sendEvent(LoadedViewModel.LoadedScreenStateEvent.DeleteFromDevice(it))
        }
    }
}

@Composable
private fun SuccessfulLoadedScreen(
    images: List<LoadedImageItem>,
    onDeleteFromDevice: (String) -> Unit
) {
    Column {
        Text(text = "Loaded")
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(
                count = images.size,
                key = { i -> images[i].id },
                contentType = { i -> images[i]::class }
            ) { i ->
                LoadedImageCard(
                    onDeleteFromDevice = { onDeleteFromDevice(images[i].id) },
                    image = images[i].bitmap!!
                )
            }
        }
    }
}

@Composable
private fun LoadedImageCard(
    modifier: Modifier = Modifier,
    onDeleteFromDevice: () -> Unit,
    image: Bitmap
) {
    Card(
        modifier = modifier.fillMaxSize()
    ) {
        Box {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = null
            )
            Card(Modifier.clickable { onDeleteFromDevice() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}