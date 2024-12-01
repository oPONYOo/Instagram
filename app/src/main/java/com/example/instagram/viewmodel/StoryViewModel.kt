package com.example.instagram.viewmodel

import androidx.lifecycle.ViewModel
import com.example.instagram.ui.model.Image
import com.example.instagram.ui.model.ProgressInfo
import com.example.instagram.ui.model.Story

class StoryViewModel : ViewModel() {
    val pages = List(2) {
        getMockStory(it)
    }

    private fun getMockStory(idx: Int) = Story( // 한 명의 스토리
        idx = idx,
        name = "지냐${idx}",
        imgList = listOf(
            getMockImage(0, "https://picsum.photos/1000/701"),
            getMockImage(1, "https://picsum.photos/400/702"),
            getMockImage(2, "https://picsum.photos/400/703")
        ),
        progressInfos = List(3) {
            getMockIndicator(it)
        }
    )

    private fun getMockImage(idx: Int, url: String) = Image(idx = idx, url = url)

    private fun getMockIndicator(idx: Int) = ProgressInfo(idx = idx, loading = true)
}