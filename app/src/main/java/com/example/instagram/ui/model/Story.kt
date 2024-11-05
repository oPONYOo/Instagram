package com.example.instagram.ui.model

data class Story(
    val idx: Int,
    val name: String,
    val imgList: List<Image>,
    val indicators: List<Indicator>,
    val imgListSize: Int = imgList.size,
    val complete: Boolean = false
)

data class Image(
    val idx: Int,
    val url: String
)
