package com.liyaan.mycompose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.liyaan.mycompose.home.MyScreen
import com.liyaan.mycompose.home.OneCenter
import com.liyaan.mycompose.home.ProjectTreeJson
import com.liyaan.mycompose.home.SortationComposable
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.FloatingActionButtonAdd
import com.liyaan.mycompose.view.MainBottomPage
import com.liyaan.mycompose.view.SootheBottomNavigation
import com.liyaan.mycompose.widgets.CustomWebView
import com.liyaan.utils.AesUtils
import com.liyaan.utils.Base64Utils

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val modifier = Modifier
//                        .size(200.dp)
//                        .background(Color(0xffff00ff))
//                    Greeting("Android",true, modifier)
//                    listComposableBug(Array<String>(10){"$it 1111"})
//                    val list  = Array<String>(10){"$it 1111"}
//                    NamePicker("测试",list, nameClick = {
//                        Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
//                    },
//                        Modifier
//                            .fillMaxSize()
//                            .padding(20.dp))
//                    BoxTextBackGround()
                    val list  = Array<String>(4){"https://kuriname.com/wp-content/uploads/2019/07/Doupo-Cangqiong-Season-3-Episode-3.jpg"}
//                    HorizontalPagerExampleItemScrollEffect(list,20.dp)
                    val unLists = Array<ImageVector>(3){
                        when(it){
                            0->Icons.Default.Add
                            1->Icons.Default.Send
                            else->Icons.Default.List
                        }
                    }
                    val lists = Array<ImageVector>(3){
                        when(it){
                            0->Icons.Default.Create
                            1->Icons.Default.List
                            else->Icons.Default.Send
                        }
                    }
                    val titles = Array<String>(3){
                        when(it){
                            0->"ADD"
                            1->"Send"
                            else->"List"
                        }
                    }
                    val selectIndex = remember{ mutableStateOf(0) }
                    val modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
//                    Scaffold (bottomBar = {SootheBottomNavigation(modifier = modifier,icons = lists, titles = titles,unicons=unLists, onClickSelectIndex = {
//                        selectIndex.value = it
//                    })}){
//                        when(selectIndex.value){
//                            0->Text(text = "aaaaa ${selectIndex.value}", modifier = Modifier.padding(it))
//                            1->Text(text = "bbbbbbbbb ${selectIndex.value}", modifier = Modifier.padding(it))
//                            else->Text(text = "ccccccccccc ${selectIndex.value}", modifier = Modifier.padding(it))
//                        }
//                    }
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val items = listOf(
                        Page.Home,
                        Page.ClassList,
                        Page.CenterIcon,
                        Page.StudyCenter,
                        Page.OneCenter
                    )
                    val modifierBottomBar = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .drawWithCache {
                            val bgImg = ContextCompat.getDrawable(
                                context,
                                R.drawable.main_nav_bg
                            )
                            onDrawBehind {
                                bgImg!!.updateBounds(
                                    0,
                                    0, // 这里可以调整中间的大按钮的上下位置。
                                    size.width.toInt(),
                                    size.height.toInt()
                                )
                                bgImg.draw(drawContext.canvas.nativeCanvas)
                            }
                        }
                    Scaffold(bottomBar = {

                        MainBottomPage(
                            navController=navController,
                            itemes = items,
                            modifier = modifierBottomBar)
                    },
                    floatingActionButton = {
                        FloatingActionButtonAdd {
                            val intent = Intent(this@MainActivity,
                                FloatingActionButtonAddActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(context,"FloatingActionButtonAdd",Toast.LENGTH_SHORT).show()
                        }
                    }, floatingActionButtonPosition = FabPosition.Center){ paddingValue->
                        NavHost(
                            navController = navController,
                            startDestination = Page.Home.route,
                        ){
                            composable(Page.Home.route){
                                MyScreen(onItemClick = {
//                                    gotoWebView(url = it)
//                                    val url = Base64Utils.encodeToString(it)
//                                    navController.navigate("gotoWebView/$url")
                                    val intent = Intent(this@MainActivity,WebViewActivity::class.java)
                                    intent.putExtra("str_url",it)
                                    startActivity(intent)
                                })

                            }
                            composable(Page.ClassList.route){
                                SortationComposable(){
                                    val intent = Intent(this@MainActivity,DetailsListActivity::class.java)
                                    intent.putExtra("str_cid","${it.id}")
                                    startActivity(intent)
                                }
                            }
                            composable(Page.CenterIcon.route){
                                Greeting(Page.CenterIcon.route,true)
//                                Text(text = "aaaaa ${Page.CenterIcon.route}", modifier = Modifier.padding(paddingValue))
                            }
                            composable(Page.StudyCenter.route){
                                ProjectTreeJson(){
                                    val intent = Intent(this@MainActivity,DetailsProjectListActivity::class.java)
                                    intent.putExtra("str_cid","${it.id}")
                                    startActivity(intent)
                                }
                            }
                            composable(Page.OneCenter.route){
                                OneCenter(this@MainActivity)
//                                Text(text = "aaaaa ${Page.OneCenter.route}", modifier = Modifier.padding(paddingValue))
                            }

                            composable("gotoWebView/{url}", arguments = listOf(
                                navArgument("url"){
                                    type = NavType.StringType
                                    nullable = false
                                }
                            )) {
                                val argument = requireNotNull(it.arguments)
                                val url = argument.getString("url") ?: "null/null"
                                val strUrl = Base64Utils.decode(url)
                                GotoWebView(navController = navController,url = strUrl)
//                                Column(Modifier.systemBarsPadding()) {
//                                    CustomWebView(url = strUrl, onBack = {
//                                        navController.popBackStack(
//                                            navController.currentDestination!!.id,true,true
//                                        )
//                                    })
//                                }
                            }
                        }
                    }
                    
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
@Composable
fun GotoWebView(navController: NavController,url:String){
    CustomWebView(url = url, onBack ={
        navController.navigateUp()
    } )
}
@Composable
fun Greeting(name: String,isShow:Boolean, modifier: Modifier = Modifier) {
    Column(modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.alpha(.5f)
        )
        if (isShow){
            Text(
                text = "Hello isShow = $isShow $name! ",
                color = Color.Gray,
                fontSize = 30.sp,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "测试", widthDp = 100, heightDp = 200, showSystemUi = false)
@Composable
fun GreetingPreview() {
    MyComposeTheme {
        Greeting("Android",true)
    }
}

@Composable
fun listComposable(list: Array<String>){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            list.forEach { 
                Text(text = it)
            }

        }
        Text(text = "${list.size}")
    }
}
@Composable
fun listComposableBug(list: Array<String>){
    var item:Int = 0
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            list.forEach {
                Text(text = it)
                item++
            }

        }
        Box {
            repeat(10) { item ++ }
        }
        Text(text = "${list.size}  $item")
    }
}
@Composable
fun NamePicker(header:String,list:Array<String>,nameClick:(String)->Unit,modifier: Modifier){
    Column {
        Text(text = "header == $header", style = MaterialTheme.typography.bodyMedium)
        TextState()
        TextStateSaveAble()
//        TestState4()
        TestState4()
        LazyColumn(modifier = modifier){
            items(list){
                NameItem(name = it, onClick = nameClick )
            }
        }

    }
}
@Composable
fun NameItem(name:String,onClick:(String)->Unit){
    Text(text = name, Modifier.clickable { onClick(name) })
}


@Composable
fun TextState(){
    Column(modifier =Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var index = remember {
            mutableStateOf(0)
        }
        Button(onClick = { 
            index.value++
        }) {
            Text(text = "ADD")
        }
        Text(text = "index=${index.value}", fontSize = 20.sp)
    }
}

@Composable
fun TextStateSaveAble(){
    Column(modifier =Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        var index = rememberSaveable{
            mutableStateOf(0)
        }
        Button(onClick = {
            index.value++
        }) {
            Text(text = "ADDSave")
        }
        Text(text = "index=${index.value}", fontSize = 20.sp)
    }
}
@Composable
fun TestState4(index:Int,onCLickChange:(Int)->Unit){
    ColumnStyle{
        Button(onClick = {onCLickChange(index+1)}) {
            Text(text = "AddState")
        }
        Text(text = "index = $index")
    }
}

//@Composable
//fun TestState4(){
//    val index = rememberSaveable{
//        mutableStateOf(0)
//    }
//    TestState4(index.value) {
//        index.value = it
//    }
//}

@Composable
fun TestState4(testViewModel: TestViewModel = viewModel()){
    val index by testViewModel.index.observeAsState()
    val data by testViewModel.data.collectAsState()
   Column() {
       TestState4(index!!) {
           testViewModel.onIndexChange(it)
       }
       TestState4(data!!) {
           testViewModel.onDataChange(it)
       }
   }
}

@Composable
fun ColumnStyle( content: @Composable ColumnScope.() -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier =Modifier.fillMaxWidth(), content = content)
}

