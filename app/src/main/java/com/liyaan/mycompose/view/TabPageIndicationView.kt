package com.liyaan.mycompose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow

import androidx.compose.material.Text
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabAndPage(){

    val titles = listOf("标签1", "标签2", "标签3", "标签4", "这是很长的标签5","标签6")
    val page = rememberPagerState(
        0
    )
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = page.currentPage,
            modifier = Modifier.wrapContentWidth(),
            edgePadding = 5.dp
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title)},
                    selected = page.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            page.scrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            pageCount = titles.size,
            state = page,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                        .fillMaxSize()
            ) {
                Text(text = titles[it], fontSize = 20.sp, color = Color.Red)
            }
        }

    }
}