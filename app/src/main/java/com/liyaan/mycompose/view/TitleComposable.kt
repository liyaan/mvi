package com.liyaan.mycompose.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liyaan.mycompose.R

@Composable
fun TitleComposable(
    modifier: Modifier= Modifier,
    title:String,
    isLeftVisible:Boolean = true,
    isRightVisible:Boolean = false,
    back:()->Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        AnimatedVisibility(visible = isLeftVisible){
            titleLeft(
                modifier =  Modifier.clickable {
                    back()
                }
            )
        }
        titleText(title)
        if(isRightVisible){
            AnimatedVisibility(visible = true){
                Image(
                    painter = painterResource(id = R.mipmap.oneself_setting),
                    contentDescription =null,
                )
            }
        }else{
            Text(text = "")
        }
        

    }
}
@Composable
fun titleText(title:String){
    Text(text = title, fontSize = 18.sp, color = Color.Black)
}

@Composable
fun titleLeft(modifier:Modifier = Modifier){
    Image(
        painter = painterResource(id = R.mipmap.title_left),
        contentDescription =null,
        modifier = modifier
    )
}