package com.example.instagram.ui.model

data class Indicator(
    val idx: Int,
    var currentProgress: Float = 0f,
    var loading: Boolean = false,
    var complete: Boolean = false
)
