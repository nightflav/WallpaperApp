package com.example.wallpaperapp.presentation.screens.bigimagescreen

import android.app.WallpaperManager
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.example.wallpaperapp.presentation.screens.ErrorScreen
import com.example.wallpaperapp.presentation.screens.LoadingScreen
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel.ImageScreenEvents.AddToFavourite
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel.ImageScreenEvents.InstallAsWallpaper
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel.ImageScreenEvents.LoadImage
import com.example.wallpaperapp.presentation.screens.bigimagescreen.BigImageViewModel.ImageScreenEvents.LoadToDevice

@Composable
fun BigImageScreen(
    modifier: Modifier = Modifier,
    viewModel: BigImageViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(state.error!!) {
            viewModel.sendEvent(
                LoadImage
            )
        }

        else -> BigImage(
            modifier = modifier,
            image = state.image!!,
            onInstallImageAsWallpaper = { viewModel.sendEvent(InstallAsWallpaper(it)) },
            onCancelButtonClickListener = { navController.popBackStack() },
            addToFavouriteClickListener = { viewModel.sendEvent(AddToFavourite) },
            loadImageClickListener = { viewModel.sendEvent(LoadToDevice) }
        )
    }
}

@Composable
fun BigImage(
    modifier: Modifier = Modifier,
    image: Bitmap,
    onInstallImageAsWallpaper: (Int) -> Unit,
    onCancelButtonClickListener: () -> Unit,
    addToFavouriteClickListener: () -> Unit,
    loadImageClickListener: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.tertiaryContainer.copy(
                            alpha = 0.5f
                        )
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onInstallImageAsWallpaper(WallpaperManager.FLAG_SYSTEM) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Install as wallpaper")
                }
                Button(
                    onClick = { onInstallImageAsWallpaper(WallpaperManager.FLAG_LOCK) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Install as lock")
                }
            }
            Row(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.tertiaryContainer.copy(
                            alpha = 0.5f
                        )
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = addToFavouriteClickListener) {
                    Text(text = "Add to favourite")
                }
                Button(onClick = loadImageClickListener) {
                    Text(text = "Save to device")
                }
                Button(onClick = onCancelButtonClickListener) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}