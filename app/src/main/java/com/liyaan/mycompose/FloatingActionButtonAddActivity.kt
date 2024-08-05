package com.liyaan.mycompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.StickHeaderView
import com.liyaan.net.model.NaviJsonData
import com.liyaan.net.model.NaviListJsonData
import com.liyaan.state.ResState
import com.liyaan.viewModel.MyViewModel

class FloatingActionButtonAddActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                StickyHeaderScreen{
                    val intent = Intent(this,WebViewActivity::class.java)
                    intent.putExtra("str_url",it.link)
                    startActivity(intent)
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderScreen(viewModel: MyViewModel = viewModel(),onSubtitleClick:(NaviListJsonData)->Unit){
    val state by viewModel.stateNaviJson.observeAsState()
    val mNaviDataList:MutableList<NaviJsonData> = remember {
        mutableListOf()
    }
    state?.let { currentState ->
        when(currentState.resState){
            ResState.Loading->{
                Log.i("aaaaaaaaaaaaaaa","开始请求")
            }
            ResState.Success->{
                Log.i("aaaaaaaaaaaaaaa","${currentState.beanList.size}")
                mNaviDataList.addAll(currentState.beanList)
            }
            else->{
                Log.i("aaaaaaaaaaaaaaa","请求失败 请求结束")
            }
        }
    }?: run {
        Log.i("aaaaaaaaa", "state为空")
        viewModel.processNaviJson()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        mNaviDataList.forEachIndexed { index, post ->
            stickyHeader {
                ListTitle(title = post.name, type = 0){

                }
            }
            itemsIndexed(post.articles){index,bean->
                ListTitle(title = bean.title, type = 1){
                    onSubtitleClick(bean)
                }
            }
        }
    }
}
@Composable
fun ListTitle(
    modifier: Modifier = Modifier,
    title: String,
    type:Int,
    onSubtitleClick:()->Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(if (type == 0) Color.Gray else Color.White)
    ) {
        Text(
            text = title,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    onSubtitleClick()
                }
                .padding(start = 10.dp))
    }
}
