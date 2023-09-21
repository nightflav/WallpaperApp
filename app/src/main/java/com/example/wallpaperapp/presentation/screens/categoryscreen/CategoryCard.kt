package com.example.wallpaperapp.presentation.screens.categoryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.presentation.model.uimodel.CategoryItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: CategoryItem,
    onCategoryClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .height(320.dp)
            .clickable {
                onCategoryClick()
            },
        shape = RoundedCornerShape(5),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = category.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = category.description,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.5f
                        )
                    )
                    .padding(8.dp, 4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 24.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                textAlign = TextAlign.Center,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp,
                softWrap = true,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        modifier = Modifier,
        category = CategoryItem(
            "id",
            "",
            "Some description"
        ),
        onCategoryClick = {}
    )
}