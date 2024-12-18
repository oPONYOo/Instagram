package com.example.instagram

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.instagram.ui.model.Image
import com.example.instagram.viewmodel.StoryViewModel
import kotlinx.coroutines.launch

@Composable
fun StoryScreen(
    modifier: Modifier, viewModel: StoryViewModel = viewModel()
) {
    val pages by rememberSaveable { mutableStateOf(viewModel.pages) }
    val pagerState = rememberPagerState(pageCount = {
        pages.size
    })

    var showImageOrder: Int by rememberSaveable {
        mutableIntStateOf(0)
    }

    val rememberCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            showImageOrder = 0
        }
    }

    // 페이지가 넘어갈 때 마다 인디케이터 초기화
    HorizontalPager(state = pagerState) { idx ->
        Box(
            modifier = modifier
                .background(Color.LightGray)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val nowStory = pages[idx]
            val nowStoryImgList = nowStory.imgList

            var currentProgress by rememberSaveable { mutableFloatStateOf(0f) }
            var loading by rememberSaveable { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            // TODO 뷰모델로 로직 옮기기
            val nowStoryIndicators by rememberSaveable {
                mutableStateOf(nowStory.progressInfos.toMutableList())
            }

            LaunchedEffect(loading) {
                loading = true
                scope.launch {
                    // 클릭해서 스토리 넘길 시에는 업데이트 하면 안됨
                    loadProgress { progress ->
                            currentProgress = progress
                    }

                    if (currentProgress == 1f) {
                        currentProgress = 0f
                        if (showImageOrder < nowStory.imgListSize - 1) {
                            showImageOrder += 1
                        } else {
                            rememberCoroutineScope.launch {
                                if (idx == pages.size) return@launch
                                pagerState.animateScrollToPage(idx + 1)
                            }
                        }
                    }
                    loading = false // Reset loading when the coroutine finishes
                }
            }
            val updateIndicator =
                nowStoryIndicators[showImageOrder].copy(currentProgress = currentProgress)
            nowStoryIndicators[showImageOrder] = updateIndicator

            StoryImage(
                imgs = nowStoryImgList,
                leftClickEvent = {
                    if (showImageOrder > 0) {
                        currentProgress = 0f
                        val temp =
                            nowStoryIndicators[showImageOrder].copy(currentProgress = currentProgress)
                        nowStoryIndicators[showImageOrder] = temp

                        showImageOrder -= 1
                    } else {
                        rememberCoroutineScope.launch {
                            if (idx == pages.size) return@launch
                            pagerState.animateScrollToPage(idx - 1)
                        }
                    }
                },
                rightClickEvent = {
                    if (showImageOrder < nowStory.imgListSize - 1) {
                        currentProgress = 1f
                        val temp =
                            nowStoryIndicators[showImageOrder].copy(currentProgress = currentProgress)
                        nowStoryIndicators[showImageOrder] = temp
                        showImageOrder += 1
                    } else {
                        rememberCoroutineScope.launch {
                            if (idx == pages.size) return@launch
                            pagerState.animateScrollToPage(idx + 1)
                        }
                    }
                }, // story 넘어가기, 완료표시
                showImageIdx = showImageOrder
            )
            StoryLinearIndicator(nowStoryIndicators)
            Text(text = "$idx", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun StoryImage(
    imgs: List<Image>,
    showImageIdx: Int,
    rightClickEvent: () -> Unit,
    leftClickEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    val image = imgs.getOrNull(showImageIdx) ?: return
    val screenWidthOffset = (LocalConfiguration.current.screenWidthDp / 2).dp
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    if (screenWidthOffset <= (offset.x).toDp()) {
                        rightClickEvent()
                    } else {
                        leftClickEvent()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CoilImage(url = image.url)
    }
}

@Preview(showBackground = true)
@Composable
fun StoryScreenPreview() {
    StoryScreen(modifier = Modifier)
}

