package com.example.instagram.ui.model

data class Story(
    val idx: Int,
    val name: String,
    val imgList: List<Image>,
    val progressInfos: List<ProgressInfo>,
    val imgListSize: Int = imgList.size,
    val complete: Boolean = false // 스토리의 마지막까지 봤는지 여부
)

data class Image(
    val idx: Int,
    val url: String
)
