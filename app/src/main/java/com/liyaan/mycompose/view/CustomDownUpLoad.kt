package com.liyaan.mycompose.view


import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun <T>UpDownLoadComposable(modifier: Modifier = Modifier,
                                  onRefresh: () -> Unit, onDownRefresh: () -> Unit,
                                  items: List<T>,
                                  listState:LazyListState  = rememberLazyListState(),
                                  contentPadding: PaddingValues = PaddingValues(0.dp),
                                 contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
                                  itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit){
    var downRefreshing by remember {
        mutableStateOf(false)
    }
    var upRefreshing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(listState){
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ==items.size-1}
            .distinctUntilChanged()
            .collect{
                Log.i("aaaaaaaaaaaaaaa ","layoutInfo visibleItemsInfo")
                downRefreshing = true
                if (items.size>=10){
                    onDownRefresh()
                }
                downRefreshing = false
            }
    }
    UpLoadComposable(modifier = modifier,refreshing = upRefreshing, onRefresh = {
        upRefreshing = true
        onRefresh()
        upRefreshing = false
    }) {
//        content(downRefreshing)
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = contentPadding,
        ) {
            itemsIndexed(
                items = items,
                contentType = contentType
            ) { index, bean ->
                itemContent(index, bean)

            }
            if (downRefreshing) {
//                    LaunchedEffect(items.size) { onLoad() }
                item{
                    Text(text = "加载更多", fontSize = 14.sp, textAlign = TextAlign.Center)
                }
            }
//            item { MoreIndicator(finishing) }
        }
    }
}