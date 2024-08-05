package com.liyaan.mycompose.view


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp

import coil.compose.rememberAsyncImagePainter



import kotlin.math.absoluteValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPageIndicatorPager(
    images: Array<String>,
    selectColor:Color,
    selectedColor:Color,
    size:Dp
    ){

    Box(modifier = Modifier.fillMaxSize()){
        val page = rememberPagerState(
           0
        )
        HorizontalPager(
            pageCount= images.size,
            state = page,
            modifier = Modifier.fillMaxSize(),
        ) {
            val painter = rememberAsyncImagePainter(model  = images[it])
            Image(
                painter = painter,
                contentDescription = "网络图片",
                modifier = Modifier.fillMaxWidth() // 根据需要调整修饰符
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
           repeat(
               page.targetPage
           ){iteration ->
               val color = if (page.currentPage == iteration) selectedColor else selectColor
               Box(
                   modifier = Modifier
                       .padding(2.dp)
                       .clip(CircleShape)
                       .background(color)
                       .size(size)
               )
           }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerExampleContentPadding(images: MutableList<String>, margin:Dp,isLooper:Boolean){
    val page = rememberPagerState(
        0
    )
    if (isLooper) {
        var currentTime by remember {
            mutableStateOf(10L)
        }
        LaunchedEffect(key1 = currentTime) {
            delay(1000)
            if (images.size>0){
                if (page.currentPage == images.size-1) {
                    page.animateScrollToPage(0)
                } else {
                    page.animateScrollToPage(page.currentPage + 1)
                }
                currentTime = System.currentTimeMillis()
            }
        }
    }
    HorizontalPager(
        pageCount = images.size,
        contentPadding = PaddingValues(horizontal = margin),
        state = page,
        modifier = Modifier.fillMaxSize().height(200.dp),
    ) {
        val painter = rememberAsyncImagePainter(model  = images[it])
        Image(
            painter = painter,
            contentDescription = "网络图片",
            contentScale=ContentScale.FillBounds,

            modifier = Modifier.fillMaxWidth().
                    height(200.dp)
                .padding(end = 30.dp).clip(shape = RoundedCornerShape(20.dp))
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp)), // 根据需要调整修饰符
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerExampleItemScrollEffect(
    images: MutableList<String>,
    margin:Dp,
    auto:Boolean,
    autoTime:Long = 3000L,
    Spacer:@Composable ColumnScope.() -> Unit
){
    //使用时间来驱动轮播的时间间隔问题

    val page = rememberPagerState(
        0
    )
    if (auto) {
        var currentTime by remember {
            mutableStateOf(10L)
        }
        LaunchedEffect(key1 = currentTime) {
            delay(autoTime)
            if (images.size>0){
                if (page.currentPage == images.size-1) {
                    page.animateScrollToPage(0)
                } else {
                    page.animateScrollToPage(page.currentPage + 1)
                }
                currentTime = System.currentTimeMillis()
            }

        }
    }
    Column(Modifier
        .fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)){

            HorizontalPager(
                pageCount = images.size,
                contentPadding = PaddingValues(horizontal = margin),
                state = page,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            ) {
                Card(
                    Modifier
                        .graphicsLayer {
                            val pageOffset = (
                                    (page.currentPage - it) + page
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            lerp(
                                start = 0.95f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                        .height(200.dp)
                ) {
                    val painter = rememberAsyncImagePainter(model  = images[it])
                    Image(
                        painter = painter,
                        contentDescription = "网络图片",
                        modifier = Modifier.height(200.dp).fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = page,
                pageCount = images.size,
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(10.dp))
        }
        Spacer()
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun <T> HorizontalPagerIndicatorExample(images: MutableList<T>, content: @Composable ColumnScope.(Int) -> Unit) {
    Column(modifier = Modifier.background(Color.Blue).fillMaxWidth().height(200.dp)) {
        val pagerState = rememberPagerState(0)
        // Display 10 items
        HorizontalPager(
            pageCount = images.size,
            state = pagerState,
            // Add 32.dp horizontal padding to 'center' the pages
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
//                Box(
//                    Modifier
//                        .background(if (page % 2 == 0) Color.Blue else Color.Yellow)
//                        .fillMaxWidth()
//                        .height(500.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = "Page: $page", color = Color.White, fontSize = 22.sp)
//                }
            content(page)
        }

//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(16.dp),
//            )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount =images.size,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

    }
}