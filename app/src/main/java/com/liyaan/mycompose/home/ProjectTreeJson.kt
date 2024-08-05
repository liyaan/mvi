package com.liyaan.mycompose.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyaan.mycompose.ui.theme.BG
import com.liyaan.net.model.ProjectList
import com.liyaan.state.ResState
import com.liyaan.viewModel.MyViewModel

@Composable
fun ProjectTreeJson(viewModel: MyViewModel = viewModel(),onItemClick:(ProjectList)->Unit){
    val state by viewModel.stateProjectJson.observeAsState()
    val mProjectList:MutableList<ProjectList> = remember {
        mutableListOf()
    }
    state?.let { currentState ->
        when(currentState.resState){
            ResState.Loading->{
                Log.i("aaaaaaaaaaaaaaa","开始请求")
            }
            ResState.Success->{
                Log.i("aaaaaaaaaaaaaaa","${currentState.beanList.size}")
                mProjectList.addAll(currentState.beanList)
            }
            else->{
                Log.i("aaaaaaaaaaaaaaa","请求失败 请求结束")
            }
        }
    }?: run {
        Log.i("aaaaaaaaa", "state为空")
        viewModel.processProjectJson()
    }
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 60.dp)
        .background(BG)){
        itemsIndexed(mProjectList){index,entity->
            ProjectListTitle(title = entity.name){
                onItemClick(entity)
            }
        }
    }
}

@Composable
fun ProjectListTitle(
    modifier: Modifier = Modifier,
    title: String,
    onSubtitleClick:()->Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onSubtitleClick()
            }
            .padding(bottom = 1.dp),
    ) {
       // .wrapContentSize(align = Alignment.Center)  //与上边代码效果一样
        //.wrapContentHeight(align = Alignment.CenterVertically)  //设置竖直居中
        //            .wrapContentWidth(align = Alignment.CenterHorizontally) //设置水平居中
        Text(
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxSize()
//                .wrapContentSize(align = Alignment.Center)
                .background(Color.White).padding(14.dp))
    }
}