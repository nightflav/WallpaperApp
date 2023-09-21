package com.example.wallpaperapp.presentation.screens.favandload.favourite

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.presentation.model.uimodel.PhotoItem
import com.example.wallpaperapp.presentation.screens.ErrorScreen
import com.example.wallpaperapp.presentation.screens.LoadingScreen

@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel
) {
    val state by viewModel.state.collectAsState()
    Log.d("TAGTAGTAG", "${state.images}")
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(e = state.error!!) {
            viewModel.sendEvent(FavouriteViewModel.FavouriteScreenEvents.LoadFavourites)
        }

        else -> FavouriteSuccessfulScreen(
            onDeleteFromFavourite = {
                viewModel.sendEvent(
                    FavouriteViewModel.FavouriteScreenEvents.RemoveFromFavourites(
                        it
                    )
                )
            },
            images = state.images
        )
    }
}

@Composable
private fun FavouriteSuccessfulScreen(
    onDeleteFromFavourite: (String) -> Unit,
    images: List<PhotoItem>
) {
    Column {
        Text(text = "Favourites")
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(
                count = images.size,
                key = { i -> images[i].id },
                contentType = { i -> images[i]::class }
            ) { i ->
                FavouriteItemCard(
                    onDeleteFromFavourite = { onDeleteFromFavourite(images[i].id) },
                    url = images[i].uri
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun FavouriteItemCard(
    onDeleteFromFavourite: () -> Unit,
    modifier: Modifier = Modifier,
    url: String,
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(15)
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(320.dp)
                    .fillMaxWidth(),
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Card(
                Modifier
                    .clickable { onDeleteFromFavourite() }
                    .padding(8.dp)
                    .height(40.dp)
                    .width(40.dp),
                shape = RoundedCornerShape(40)) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            }
        }
    }
}