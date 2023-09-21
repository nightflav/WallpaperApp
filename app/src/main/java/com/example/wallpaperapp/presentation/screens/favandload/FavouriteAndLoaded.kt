package com.example.wallpaperapp.presentation.screens.favandload

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavouritesAndLoadedImages(
    onFavouriteClickListener: () -> Unit,
    onLoadedClickListener: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpecialCategoryCard(
            title = "Favorites",
            modifier = Modifier
                .weight(1f)
                .clickable { onFavouriteClickListener() })
        SpecialCategoryCard(
            title = "Loaded",
            modifier = Modifier
                .weight(1f)
                .clickable { onLoadedClickListener() })
    }
}

@Composable
private fun SpecialCategoryCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
                .fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun FavAndLoadedPreview() {
    FavouritesAndLoadedImages({}) {}
}