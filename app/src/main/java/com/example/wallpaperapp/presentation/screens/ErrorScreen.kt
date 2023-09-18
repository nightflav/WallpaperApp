package com.example.wallpaperapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(e: Throwable, onAgainButtonClickListener: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        e.message?.let { mes ->
            Text(
                text = mes,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = onAgainButtonClickListener) {
            Text("Try again")
        }
    }
}