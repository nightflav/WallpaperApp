package com.example.wallpaperapp.presentation.screens.favandload

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavouritesAndLoadedImages() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpecialCategoryCard(title = "Favorites")
        SpecialCategoryCard(title = "Loaded")
    }
}

@Composable
private fun SpecialCategoryCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .heightIn(max = 200.dp),
        shape = RoundedCornerShape(15),
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
        )
    }
}

@Preview
@Composable
fun FavAndLoadedPreview() {
    FavouritesAndLoadedImages()
}