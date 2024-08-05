package com.liyaan.mycompose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.ScrollableTabRow

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liyaan.mycompose.R
import com.liyaan.mycompose.ScrollableTabRowDemo
import kotlinx.coroutines.launch

@Composable
fun StickHeaderTabPageView(){
    val itemOnes:MutableList<String> = remember {
        mutableStateListOf()
    }
    var state = remember { mutableStateOf(0) }
    val titles = listOf("标签1", "标签2", "标签3", "标签4", "这是很长的标签5")
    StickHeaderTabPage(
        content = {
            ScrollableTabRow(
                selectedTabIndex = state.value,
                modifier = Modifier.wrapContentWidth(),
                edgePadding = 16.dp
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = state.value == index,
                        onClick = {
                            state.value = index
                            itemOnes.add("选中的 index = $index")
                        }
                    )
                }
            }
        },
        itemHeaderContent ={
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)){
                Image(
                    painter = painterResource(id = R.mipmap.gongkaiclass_icon),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            itemOnes.forEachIndexed { _, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item)
                }
            }
        }


    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickHeaderTabPage(
    modifier: Modifier = Modifier,
    items:List<Int> = listOf(0),
    content: @Composable() (LazyItemScope.() -> Unit?),
    itemHeaderContent: @Composable LazyItemScope.() -> Unit,
    itemContent: @Composable LazyItemScope.() -> Unit
){

    LazyColumn(
        modifier = modifier
    ){
        items.forEachIndexed { index, post ->
            item {
                itemHeaderContent()
            }
            stickyHeader {
                content()
            }
            item {
                itemContent()
            }

        }
    }
}

