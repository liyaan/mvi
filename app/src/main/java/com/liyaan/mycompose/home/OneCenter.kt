package com.liyaan.mycompose.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liyaan.mycompose.R
import com.liyaan.mycompose.TabRowActivity
import com.liyaan.mycompose.ui.theme.BG

@Composable
fun OneCenter(context: Context){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .height(60.dp)
               .background(Color.White)
               .padding(start = 10.dp),
           verticalAlignment = Alignment.CenterVertically
       ){
            Image(
                painter = painterResource(id = R.mipmap.home_header_photo),
                contentDescription = "",
            )
           Spacer(modifier = Modifier.width(10.dp))
           Text(text = "RowTab导航", fontSize = 16.sp)
       }
        itemView(){
            val intent = Intent(context,TabRowActivity::class.java)
            context.startActivity(intent)
        }
    }
}
@Composable
fun itemView(onClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .height(60.dp)
            .background(Color.White).clickable {
                onClick()
            }
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.CenterStart
    ){
        Image(
            painter = painterResource(id = R.mipmap.home_header_photo),
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "用户名称", fontSize = 16.sp, modifier = Modifier.padding(start = 50.dp))
        Icon(
            painter = painterResource(id = R.mipmap.ceshi_jiantou),
            contentDescription ="", modifier = Modifier.align(Alignment.CenterEnd))
    }
}