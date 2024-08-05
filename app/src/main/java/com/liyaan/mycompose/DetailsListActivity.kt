package com.liyaan.mycompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.UpDownLoadComposable
import com.liyaan.net.model.TreeJsonListData
import com.liyaan.state.ResState
import com.liyaan.viewModel.MyViewModel

class DetailsListActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cid: String? = intent.getStringExtra("str_cid");
        setContent {
            MyComposeTheme {
                if (cid != null) {
                    DetailsListView(cid = cid){
                        val intent = Intent(this@DetailsListActivity,WebViewActivity::class.java)
                        intent.putExtra("str_url",it.link)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
@Composable
fun DetailsListView(viewModel: MyViewModel = viewModel(), cid:String, onItemClick:(TreeJsonListData)->Unit){
    val state by viewModel.stateTreeListJson.observeAsState()
    val mDataLists:MutableList<TreeJsonListData> = remember {
        mutableListOf()
    }
    var page by remember {
        mutableStateOf(0)
    }
    state?.let { currentState ->
        when(currentState.resState){
            ResState.Loading->{
                Log.i("aaaaaaaaaaaaaaa","开始请求")
            }
            ResState.Success->{
                currentState.beanEntity?.let {
                    if (page==0)mDataLists.clear()
                    if (page<it.pageCount){
                        mDataLists.addAll(it.datas)
                    }else{
                        Log.i("aaaaaaaaaaa", "加载无更多数据")
                    }
                }
            }
            else->{
                Log.i("aaaaaaaaaaa", "请求失败")
            }
        }
    }?: run {
        viewModel.processTreeListJson(page,cid)
    }
    UpDownLoadComposable( onRefresh = {
        page = 0
        viewModel.processTreeListJson(page,cid)
    }, onDownRefresh = {
        page+=1
        viewModel.processTreeListJson(page,cid)

    },items= mDataLists,){index,item->
        ItemComposable(item){
            onItemClick(item)
        }
    }
}
@Composable
fun ItemComposable(item:TreeJsonListData,onItemClick:(TreeJsonListData)->Unit){
    Card(modifier = Modifier.padding(5.dp).clickable {
        onItemClick(item)
    }) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))
            Text(
                text = item.chapterName,
                fontSize = 16.sp,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))
            Text(
                text = item.niceShareDate,
                fontSize = 16.sp,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}