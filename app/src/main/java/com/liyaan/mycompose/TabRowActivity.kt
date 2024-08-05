package com.liyaan.mycompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.StickHeaderTabPageView
import com.liyaan.mycompose.view.TabAndPage

class TabRowActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
//                TabView()
                StickHeaderTabPageView()
            }
        }
    }
}
@Composable
fun TabView(){
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")
    var selectedIndex by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color.Black,
            modifier = Modifier.background(Color.Gray),
            indicator = {
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(it[selectedIndex])
                        .height(2.dp),
                    color = Color.Blue
                )
            }
        ) {
            titles.forEachIndexed { index, s ->
                Tab(selected = selectedIndex==index, onClick = {
                    selectedIndex = index
                }, selectedContentColor = Color.Green,text={
                    Text(text = s, color = if (selectedIndex==index) Color.White else Color.Gray, fontSize = 18.sp)
                })

            }
        }
        Text(text = "选中$selectedIndex ${titles[selectedIndex]}")
        Spacer(modifier = Modifier.height(20.dp))
//        ScrollableTabRowDemo()

        TabAndPage()
    }
   
}

@Composable
fun ScrollableTabRowDemo() {
    var state = remember { mutableStateOf(0) }
    val titles = listOf("标签1", "标签2", "标签3", "标签4", "这是很长的标签5")
    Column {
        ScrollableTabRow(
            selectedTabIndex = state.value,
            modifier = Modifier.wrapContentWidth(),
            edgePadding = 16.dp
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state.value == index,
                    onClick = { state.value = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "第${state.value + 1}个标签被选中了",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}