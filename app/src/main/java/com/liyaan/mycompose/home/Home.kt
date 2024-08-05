package com.liyaan.mycompose.home


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.liyaan.mycompose.ui.theme.BG
import com.liyaan.mycompose.view.HorizontalPagerExampleContentPadding
import com.liyaan.mycompose.view.HorizontalPagerExampleItemScrollEffect
import com.liyaan.mycompose.view.UpLoadComposable
import com.liyaan.mycompose.widgets.CustomWebView
import com.liyaan.net.model.BannerItem
import com.liyaan.state.ResState

import com.liyaan.viewModel.MyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyScreen(viewModel: MyViewModel = viewModel(),onItemClick:(String)->Unit) {
    val state by viewModel.state.observeAsState()
    val listBanner:MutableList<BannerItem> = remember {
        mutableListOf()
    }
    val listImage:MutableList<String> = remember {
        mutableListOf()
    }
    var refreshing by remember {
        mutableStateOf(false)
    }

    state?.let { currentState ->
        when(currentState.resState){
            ResState.Loading->{
//                listBanner.clear()
//                listImage.clear()
                Log.i("aaaaaaaaaaaaaaa","开始请求")
            }
            ResState.Success->{
//                refreshing = true
                Log.i("aaaaaaaaaaaaaaa","${currentState.beanList.size}")
                if (listImage.size>0)listImage.clear()
                if (listBanner.size>0)listBanner.clear()

                currentState.beanList.forEach {
                    Log.i("aaaaaaaaaaaaaaaaa",it.title)
                    listImage.add(it.imagePath)
                }
                listBanner.addAll(currentState.beanList)
                Log.i("aaaaaaaaaaaaaaa","请求结束")
//                state?.resState=ResState.InitRes
//                isRefreshing.value = false
                refreshing = false
            }
            else->{
                Log.i("aaaaaaaaa", "请求失败")
                Log.i("aaaaaaaaaaaaaaa","请求失败 请求结束")
//                isRefreshing.value = false
            }
        }
    }?: run {
        Log.i("aaaaaaaaa", "state为空")
        viewModel.processIntent()
//        isRefreshing.value = false
    }

//    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
//
//    })
    val scope = rememberCoroutineScope()
    UpLoadComposable(
        modifier = Modifier.background(BG),
        refreshing = refreshing,
        onRefresh={
            scope.launch {
                refreshing = true
                delay(2000)
                viewModel.processIntent()
                refreshing = false
            }

        }
    ){
        LazyColumn (modifier = Modifier.padding(bottom = 60.dp, top = 20.dp)){
            item {
                HorizontalPagerExampleItemScrollEffect(
                    listImage,
                    10.dp,true){
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                }
            }
            item{
                LazyRow (horizontalArrangement=Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    items(listBanner.size){
                        Column(modifier = Modifier
                            .width(120.dp)
                            .height(165.dp)
                            .padding(10.dp)) {
                            val painter = rememberAsyncImagePainter(model  = listBanner[it].imagePath)
                            Image(
                                painter =painter ,
                                contentDescription =null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .padding(bottom = 5.dp),
                                contentScale = ContentScale.FillHeight
                            )
                            Text(listBanner[it].title,
                                style = TextStyle(color = Color.Black, fontSize = TextUnit(15f,
                                    TextUnitType.Sp
                                ), fontWeight = FontWeight.Bold), overflow = TextOverflow.Ellipsis, maxLines = 1
                            )
                            Text(listBanner[it].desc,style = TextStyle(color = Color.Gray, fontSize = TextUnit(12f,
                                TextUnitType.Sp
                            )),overflow = TextOverflow.Ellipsis, maxLines = 1)
                        }
                    }
                }
            }
            item {
                HorizontalPagerExampleContentPadding(listImage,22.dp,true)
            }
            items(listBanner.size){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                            state?.resState =ResState.InitRes
                        onItemClick(listBanner[it].url)
                    }) {
                    val painter = rememberAsyncImagePainter(model  = listBanner[it].imagePath)
                    Image(
                        painter =painter ,
                        contentDescription =null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Text( listBanner[it].title,
                        style = TextStyle(color = Color.Black, fontSize = TextUnit(20f,
                            TextUnitType.Sp
                        ), fontWeight = FontWeight.Bold))
                    Text( listBanner[it].desc,style = TextStyle(color = Color.Gray, fontSize = TextUnit(16f,
                        TextUnitType.Sp
                    )))
                }
            }
        }
    }
//    Box (modifier = Modifier.background(BG).pullRefresh(refreshState)){
////        Button(onClick = { viewModel.processIntent() }) {
////            Text("Load Data")
////        }
//
//            LazyColumn (modifier = Modifier.padding(bottom = 60.dp)){
//                item {
//                    HorizontalPagerExampleItemScrollEffect(
//                        listImage,
//                        10.dp,true){
//                        Spacer(modifier = Modifier
//                            .fillMaxWidth()
//                            .height(10.dp))
//                    }
//                }
//                item{
//                    LazyRow (horizontalArrangement=Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
//                        items(listBanner.size){
//                            Column(modifier = Modifier
//                                .width(120.dp)
//                                .height(165.dp)
//                                .padding(10.dp)) {
//                                val painter = rememberAsyncImagePainter(model  = listBanner[it].imagePath)
//                                Image(
//                                    painter =painter ,
//                                    contentDescription =null,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(110.dp)
//                                        .padding(bottom = 5.dp),
//                                    contentScale = ContentScale.FillHeight
//                                )
//                                Text(listBanner[it].title,
//                                    style = TextStyle(color = Color.Black, fontSize = TextUnit(15f,
//                                        TextUnitType.Sp
//                                    ), fontWeight = FontWeight.Bold), overflow = TextOverflow.Ellipsis, maxLines = 1
//                                )
//                                Text(listBanner[it].desc,style = TextStyle(color = Color.Gray, fontSize = TextUnit(12f,
//                                    TextUnitType.Sp
//                                )),overflow = TextOverflow.Ellipsis, maxLines = 1)
//                            }
//                        }
//                    }
//                }
//                item {
//                    HorizontalPagerExampleContentPadding(listImage,22.dp,true)
//                }
//                items(listBanner.size){
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
////                            state?.resState =ResState.InitRes
//                            onItemClick(listBanner[it].url)
//                        }) {
//                        val painter = rememberAsyncImagePainter(model  = listBanner[it].imagePath)
//                        Image(
//                            painter =painter ,
//                            contentDescription =null,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(200.dp),
//                            contentScale = ContentScale.FillWidth
//                        )
//                        Text( listBanner[it].title,
//                            style = TextStyle(color = Color.Black, fontSize = TextUnit(20f,
//                                TextUnitType.Sp
//                            ), fontWeight = FontWeight.Bold))
//                        Text( listBanner[it].desc,style = TextStyle(color = Color.Gray, fontSize = TextUnit(16f,
//                            TextUnitType.Sp
//                        )))
//                    }
//                }
//            }
//        PullRefreshIndicator(refreshing = refreshing, state = refreshState,
//            modifier = Modifier.align(alignment = Alignment.TopCenter))
//        }


}