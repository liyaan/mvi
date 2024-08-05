package com.liyaan.mycompose.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyaan.mycompose.ui.theme.BG
import com.liyaan.net.model.BannerItem
import com.liyaan.net.model.TreeJsonChildren
import com.liyaan.net.model.TreeJsonData
import com.liyaan.state.ResState
import com.liyaan.viewModel.MyViewModel

@Composable
fun SortationComposable(viewModel: MyViewModel = viewModel(),onClickItem:(TreeJsonChildren)->Unit){
    val state by viewModel.stateTreeJson.observeAsState()
    val listJsonData:MutableList<TreeJsonData> = remember {
        mutableListOf()
    }
    val listJsonChildren:MutableList<TreeJsonChildren> = remember {
        mutableStateListOf()
    }
    var selectIndex by remember {
        mutableStateOf(0)
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
                Log.i("aaaaaaaaaaaaaaa","${currentState.beanList}")

                listJsonData.addAll(currentState.beanList)
                listJsonChildren.addAll(listJsonData[0].children)
            }
            else->{
                Log.i("aaaaaaaaa", "请求失败")
                Log.i("aaaaaaaaaaaaaaa","请求失败 请求结束")
//                isRefreshing.value = false
            }
        }
    }?: run {
        Log.i("aaaaaaaaa", "state为空")
        viewModel.processTreeJson()
//        isRefreshing.value = false
    }

    Row(modifier = Modifier.fillMaxSize().padding(bottom = 60.dp)) {
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)){
            items(listJsonData.size){ index->
                val itemBean = listJsonData[index]
                LazyColumnText(modifier = Modifier.background(
                    if (selectIndex==index) Color.Gray else BG
                ), name = itemBean.name, index = index,selectIndex = selectIndex){
                    selectIndex = index
                    listJsonChildren.clear()
                    listJsonChildren.addAll(itemBean.children)
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(5.dp))
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .weight(2f)){
            itemsIndexed(listJsonChildren){ index,itemBean->
                LazyColumnText(Modifier.background(BG),name = itemBean.name, index = index){
                    onClickItem(itemBean)
                }
            }
        }
    }
}

@Composable
fun LazyColumnText(modifier: Modifier =Modifier,name:String,index:Int,selectIndex:Int = -1,clickItem:(Int)->Unit){

   Box(modifier = Modifier.padding(top=1.dp, start = 1.dp)) {
       Box(
           modifier = modifier
               .fillMaxWidth()
               .height(40.dp)
               .clickable {
                   Log.i("aaaaaaaaaaaaaa ", "$name == $index")
                   clickItem(index)
               },contentAlignment = Alignment.Center) {
           Text(
               text = name,
               fontSize = 16.sp,
               textAlign = TextAlign.Center,
               overflow = TextOverflow.Ellipsis,
               color = if (selectIndex == index) Color.White else Color.Black
           )
       }
   }
}