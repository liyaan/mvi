package com.liyaan.mycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.TitleComposable
import com.liyaan.mycompose.widgets.CustomProWebView
import com.liyaan.mycompose.widgets.CustomWebView

class WebViewActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url: String? = intent.getStringExtra("str_url")
        setContent {
            MyComposeTheme {
                if (url != null) {
                    GotoWebView(url){
                        finish()
                    }
                }else{
                    UriIsNull(){
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun GotoWebView( url:String,back:()->Unit){
    CustomProWebView(url = url, onBack ={
        webView ->
        run {
            if (webView != null && webView.canGoBack()) {
                webView.goBack()
            } else {
                back()
            }
        }
    },progressColor = Color.Blue, progressTrackColor = Color.White ){
        TitleComposable(
            title = "WebView", isLeftVisible = true, isRightVisible = false,back={
                back()
            }
        )
    }
}
@Composable
fun UriIsNull(back:()->Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleComposable(
            title = "WebView", isLeftVisible = true, isRightVisible = false,back={
                back()
            }
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = "url不能为空",
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { back() })
        }
    }
}