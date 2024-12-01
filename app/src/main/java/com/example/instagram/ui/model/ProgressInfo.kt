package com.example.instagram.ui.model

data class ProgressInfo(
    val idx: Int,
    var currentProgress: Float = 0.0f,
    var loading: Boolean = false,
    var complete: Boolean = false
)
