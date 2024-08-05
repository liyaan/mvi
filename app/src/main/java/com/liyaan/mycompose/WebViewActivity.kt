package com.liyaan.mycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import com.liyaan.mycompose.view.TitleComposable
import com.liyaan.mycompose.widgets.CustomProWebView
import com.liyaan.mycompose.widgets.CustomWebView

class WebViewActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url: String? = intent.getStringExtra("str_url");
        setContent {
            MyComposeTheme {
                if (url != null) {
                    GotoWebView(url){
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