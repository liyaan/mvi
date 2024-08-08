package com.liyaan.mycompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.liyaan.mycompose.ui.theme.MyComposeTheme

class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                SplashComposable(){
                    val intent = Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
@Composable
fun SplashComposable(nextLayout:()->Unit){
    val alphaAnimation = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000)
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            alpha = alphaAnimation.value
        }
        .background(Color.Gray),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.mipmap.refreshing_10),
            contentDescription = "",
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alphaAnimation.value
                    scaleX = alphaAnimation.value
                    scaleY = alphaAnimation.value
                },
        )
        if (alphaAnimation.value==1f){
            nextLayout()
        }
    }
}