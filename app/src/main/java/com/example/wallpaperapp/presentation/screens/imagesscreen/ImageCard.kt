package com.example.wallpaperapp.presentation.screens.imagesscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.data.uimodel.PhotoItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    imageModel: PhotoItem,
    onImageSelected: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(15),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .clickable {
                onImageSelected()
            }
            .fillMaxWidth()
            .height(320.dp)
    ) {
        GlideImage(
            model = imageModel.url,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ImageCardPreview() {
    ImageCard(
        imageModel = PhotoItem(
            id = "1",
            url = "",
        )
    ) {}
}