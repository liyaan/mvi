package com.liyaan.mycompose.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpLoadComposable(modifier: Modifier = Modifier,refreshing:Boolean, onRefresh: () -> Unit,
                     content: @Composable BoxScope.() -> Unit){
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        onRefresh()
    })
    Box(modifier = modifier.pullRefresh(refreshState)){
        content()
        PullRefreshIndicator(refreshing = refreshing, state = refreshState,
            modifier = Modifier.align(alignment = Alignment.TopCenter))
    }
}