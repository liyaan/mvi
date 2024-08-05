package com.liyaan.mycompose.grap

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalTextApi::class)
@Composable
fun CircleTextMeasurer(){
    val textMeasurer = rememberTextMeasurer()
    Box(modifier =
    Modifier
        .fillMaxSize()
        .padding(16.dp)
        .drawWithContent {
            drawRect(Color.LightGray)

            drawText(
                textMeasurer = textMeasurer,
                "降Compose十八掌",
                topLeft = Offset(size.width / 4f, size.height / 2.2f)
            )

            drawCircle(
                color = Color.Magenta,
                radius = size.width / 10f,
                center = Offset(size.width / 1.8f, size.height / 3f)
            )

            drawCircle(
                color = Color.Yellow,
                radius = size.width / 12f,
                center = Offset(size.width / 1.6f, size.height / 4.35f)
            )

            drawCircle(
                color = Color.Green,
                radius = size.width / 14f,
                center = Offset(size.width / 1.46f, size.height / 7f)
            )
        })
}

@Composable
fun CircleTextMeasurer2(){
    OneSelfColumModifier {
        Text(text = """
            “降龙十八掌可说是【武学中的巅峰绝诣】，当真是无坚不摧、无固不破。虽招数有限，但每一招均具绝大威力。
                北宋年间，丐帮帮主萧峰以此邀斗天下英雄，极少有人能挡得他三招两式，气盖当世，群豪束手。
                当时共有“降龙廿八掌”，后经萧峰及他义弟虚竹子删繁就简，取精用宏，改为降龙十八掌，掌力更厚。
                这掌法传到洪七公手上，在华山绝顶与王重阳、黄药师等人论剑时施展出来，王重阳等尽皆称道。”
        """.trimIndent(),
        modifier =
        Modifier
            .padding(16.dp)
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E8240),
                        Color(0xFF42A565),
                        Color(0xFFE2E575)
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
            }
            .padding(16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun OneSelfColumModifier(content: @Composable ColumnScope.() -> Unit){
    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    val modifier = Modifier
        .fillMaxSize()
        .pointerInput("dragging") {
            detectDragGestures { change, dragAmount ->
                pointerOffset += dragAmount
            }
        }
        .onSizeChanged {
            pointerOffset = Offset(it.width / 2f, it.height / 2f)
        }
        .drawWithContent {
            drawContent()
            drawRect(
                Brush.radialGradient(
                    listOf(Color.Transparent, Color.Black),
                    center = pointerOffset,
                    radius = 100.dp.toPx()
                )
            )
        }
    Column (
        modifier = modifier,
        content = content
            )
}
@Composable
fun BoxTextBackGround(){
    val list =            listOf(
                Color(0xFF9E82F0),
                Color(0xFF42A5F5),
                Color(0xFFE2E575)
            )
    OneSelfBoxNotStyle {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
//            Text(text = "降Compose十八掌!", modifier = drawRoundRect(16.dp,0xFFBBAAEE,10.dp))
//            OneSelfBoxOriginStyle {
//                Text(text = "降Compose十八掌!", modifier = linearGradient(16.dp,10.dp,list))
//            }
//            Box(modifier = CircleShapeOne(200.dp,Color(0xFFF06292))) {
//                Text(text = "降Compose十八掌!",style = TextStyle(color = Color.Black, fontSize = 20.sp),
//                    modifier = Modifier.align(Alignment.Center))
//            }
            Box(modifier = Modifier.size(200.dp).clip(RectangleShape).border(2.dp,Color.Black)
                .graphicsLayer {
                    clip=true
                    shape = CircleShape
                    translationX = 50.dp.toPx()
                    translationY = 50.dp.toPx()
                }.background(Color(0xFFF06292))){
                Text(
                    "降Compose十八掌",
                    style = TextStyle(color = Color.Black, fontSize = 36.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFF4DB6AC))
            )
        }

    }
}

@Composable
fun OneSelfBoxNotStyle( content:@Composable BoxScope.() -> Unit){
    Box(content = content)
}

@Composable
fun OneSelfBoxOriginStyle( content:@Composable BoxScope.() -> Unit){
    Box(content = content, modifier = Modifier.graphicsLayer {
        scaleX = 1.1f
        scaleY = 1.6f
        translationX = 30.dp.toPx()
        translationY = 50.dp.toPx()
        alpha = 0.7f
        rotationX = 10f
        rotationY = 5f
    })
}

fun drawRoundRect(all: Dp,color: Long,value: Dp):Modifier{
    return Modifier
        .drawBehind {
            drawRoundRect(
                Color(color),
                cornerRadius = CornerRadius(value.toPx())
            )
        }
        .padding(all)
}
fun linearGradient(all: Dp,value: Dp,list:List<Color>):Modifier{
    return Modifier
        .drawBehind {
            val brush = Brush.linearGradient(
                list
            )
            drawRoundRect(
                brush,
                cornerRadius = CornerRadius(value.toPx())
            )
        }
        .padding(all)
}

fun CircleShapeOne(size: Dp,color: Color):Modifier{
    return Modifier
        .size(size)
        .graphicsLayer {
            clip = true
            shape = CircleShape
        }
        .background(color = color)
}