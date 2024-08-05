package com.liyaan.mycompose

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.background

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.AsyncImage
import com.liyaan.mycompose.ui.theme.BG
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.TitleComposable

import com.liyaan.net.model.ProjectJsonListData

class ProjectListDetailsActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cid: ProjectJsonListData? =
            intent.getSerializableExtra("project_bean") as ProjectJsonListData?;
        setContent {
            MyComposeTheme {
                if (cid != null) {
                    ProjectListDetailsView(projectBean = cid,onClick={
                        val intent = Intent(this,WebViewActivity::class.java)
                        intent.putExtra("str_url",it)
                        startActivity(intent)
                    }, back = {
                        finish()
                    })
                }
            }
        }
    }
}
@Composable
fun ProjectListDetailsView(projectBean:ProjectJsonListData, back:()->Unit,
                           onClick:(String)->Unit){
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier = Modifier.fillMaxSize()) {


        LazyColumn(modifier = Modifier
            .background(BG)
            .padding(bottom = 45.dp, top = 52.dp)
            ){
            item { 
                AsyncImage(
                    model = projectBean.envelopePic,
                    contentDescription = ""
                )
            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth())
                    Text(text = projectBean.title, fontSize = 18.sp)
                    Text(text = projectBean.desc, fontSize = 16.sp, color = Color.Gray,
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                    Text(text = "作者：${projectBean.author}", fontSize = 16.sp,)
                    ClickableText(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(color = Color.Black, fontSize = 16.sp)
                            ){
                                append("git地址")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    fontSize = 16.sp,
                                    textDecoration = TextDecoration.Underline,
                                )
                            ){
                                append(projectBean.projectLink)
                            }
                        },
                        onTextLayout={},
                        onClick =  {
                            onClick(projectBean.projectLink)
                        }

                    )
                    Text(text = projectBean.niceDate, fontSize = 16.sp)
                }
            }
        }
        TitleComposable(title = "项目介绍", modifier = Modifier.align(Alignment.TopCenter)) {
            back()
        }
        Button(
            onClick = { onClick(projectBean.link)},
            shape = CircleShape,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (interactionSource.collectIsPressedAsState().value) Color.White else Color.White,
                backgroundColor =  if (interactionSource.collectIsPressedAsState().value)
                    MaterialTheme.colors.primary else Color.Gray,
            )
        ) {
            Text(text = "网页详情",fontSize = 16.sp)
        }
    }
    
}
