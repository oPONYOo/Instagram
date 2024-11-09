package com.example.instagram

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.instagram.ui.model.Indicator
import kotlinx.coroutines.delay

@Composable
fun StoryLinearIndicator(indicators: List<Indicator>) {
    val indicatorSize = LocalConfiguration.current.screenWidthDp / (indicators.size)
    LazyRow(
    ) {
        items(items = indicators, key = { indicator -> indicator.idx }) {
            LinearProgressIndicator(
                progress = it.currentProgress,
                Modifier
                    .width(indicatorSize.dp)
                    .padding(horizontal = 2.5.dp),
                color = Color.Red
            )
        }
    }
}


/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@Composable
@Preview(showBackground = true)
fun StoryIndicatorPreview() {
//    StoryLinearTest()
}

/*
@Composable
private fun StoryLinearTest() {
    val list = List(2) {
        getMockIndicator(it)
    }
    val currentWidth = LocalConfiguration.current.screenWidthDp
    LazyRow(
    ) {
        items(list) { item ->
            LinearProgressIndicator(
                modifier = Modifier
                    .width((currentWidth / 2).dp),
                progress = 0.5f,
                color = Color.Red
            )
        }
    }
}*/
