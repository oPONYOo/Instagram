package com.example.instagram

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

@Composable
fun CoilImage(url: String, contentDescription: String = "") {
    AsyncImage(
        model = url,
        contentDescription = contentDescription
    )
}