package com.example.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.instagram.ui.theme.InstagramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    StoryScreen()
                }
            }
        }
    }
}

@Composable
fun FeedHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Profile()
            Text(text = "kakaobank")
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "Localized description"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedImagePager(
    modifier: Modifier = Modifier,
    pageSize: Int = 5,
    pagerState: PagerState = rememberPagerState(pageCount = { pageSize })
) {
    HorizontalPager(state = pagerState) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Yellow),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun FeedEvent(
    modifier: Modifier = Modifier
) {
    // 좋아요, 댓글, 공유, 북마크
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Localized description"
                )
            }
        }

        IconButton(onClick = { /* do something */ }) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Localized description"
            )
        }
    }
}

@Composable
fun FeedContent(
    modifier: Modifier = Modifier,
    likes: Int = 17,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    text: String = "맛있는 수박 냠냠냠냠맛있는"
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }
    Column(modifier = modifier.padding(start = 5.dp)) {
        Row {
            // 숫자만 bold
            Text(text = "좋아요 ${likes}개")
        }
        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(text = "j_ina__", fontWeight = FontWeight.Bold)
            Text(
                text = finalText,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                onTextLayout = { textLayoutResultState.value = it },
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .clickable(enabled = isClickable) { isExpanded = !isExpanded }
                    .animateContentSize(),
                overflow = overflow,
            )
            Text(
                text = "더 보기",
                fontWeight = FontWeight.Light,
                color = Color.Gray,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ConditionalText(content: String) {

    val minimumLineLength = 2   //Change this to your desired value

    //Adding States
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else minimumLineLength

    Column(modifier = Modifier.padding(start = 35.dp, end = 5.dp)) {
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,                   //Make sure to add this line
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > minimumLineLength - 1) {           //Adding this check to avoid ArrayIndexOutOfBounds Exception
                    if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) showReadMoreButtonState =
                        true
                }
            }
        )
        if (showReadMoreButtonState) {
            Text(
                text = if (expandedState) "Read Less" else "Read More",
                color = Color.Gray,

                modifier = Modifier.clickable {
                    expandedState = !expandedState
                },

                style = MaterialTheme.typography.bodySmall

            )
        }

    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InstagramTheme {
        Column {
/*            FeedHeader()
            FeedImagePager()*/
            FeedEvent()
            FeedContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConditionalTextPreview() {
    ConditionalText("맛있는 수박 냠냠냠냠맛있는 수박 냠냠냠냠 맛있는 수박 냠냠냠냠냠 맛있는 수박맛있는 수박 냠냠냠냠맛있는 수박 냠냠냠냠 맛있는 수박 냠냠냠냠냠 맛있는 수박맛있는 수박 냠냠냠냠맛있는 수박 냠냠냠냠 맛있는 수박 냠냠냠냠냠 맛있는 수박맛있는 수박 냠냠냠냠맛있는 수박 냠냠냠냠 맛있는 수박 냠냠냠냠냠 맛있는 수박맛있는 수박 냠냠냠냠맛있는 수박 냠냠냠냠 맛있는 수박 냠냠냠냠냠 맛있는 수박")
}
