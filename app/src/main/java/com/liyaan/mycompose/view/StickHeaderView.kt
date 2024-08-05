package com.liyaan.mycompose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T,B>StickHeaderView(
    modifier: Modifier = Modifier,
    items:MutableList<T>,
    content: @Composable LazyItemScope.(T) -> Unit,
){
    LazyColumn(
        modifier = modifier
    ){
        items.forEachIndexed { index, post ->
            stickyHeader {
                content(post)
            }

        }
    }
}