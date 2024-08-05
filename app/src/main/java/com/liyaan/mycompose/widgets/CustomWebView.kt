package com.liyaan.mycompose.widgets

import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProWebView(
    modifier: Modifier = Modifier,
    url:String,
    onBack: (webView: WebView?) -> Unit,
    initSettings: (webSettings: WebSettings?) -> Unit = {},
    onShouldOverrideUrlLoading: ((view: WebView?, request: WebResourceRequest?) -> Boolean)? = null,
    onReceivedError: (error: WebResourceError?) -> Unit = {},
    progressColor: Color = ProgressIndicatorDefaults.linearColor,
    progressTrackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
    webViewModifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit
){
    var progress by remember {
        mutableStateOf(0f)
    }
    Column(modifier=modifier) {
        title()
        AnimatedVisibility(visible = progress<=0.95){
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
                color = progressColor,
                trackColor = progressTrackColor
            )
        }
        CustomWebView(
            url = url,
            onBack =onBack,
            modifier = webViewModifier,
            initSettings = initSettings,
            onShouldOverrideUrlLoading = onShouldOverrideUrlLoading,
            onReceivedError = onReceivedError,
            onProgressChange = {
                progress = it.toFloat()/100
            }
        )
    }
}