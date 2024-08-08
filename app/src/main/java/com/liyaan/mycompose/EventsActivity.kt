package com.liyaan.mycompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import kotlin.math.roundToInt


class EventsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                DraggableText()
            }
        }
    }
}
@Composable
fun LogPointerEvents(filter: PointerEventType? = null) {
    var log by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(log)
        Box(
            Modifier
                .size(240.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent()
//                            // handle pointer event
//                            if (filter == null || event.type == filter) {
//                                log = "${event.type}, ${event.changes.first().position}"
//                            }
//                        }
//                    }
                    awaitEachGesture {
                        val down = awaitFirstDown().also {
                            log = "Action Down"
                        }
                        var change = awaitTouchSlopOrCancellation(down.id) { ch, _ ->
                            if (ch.positionChange() != Offset.Zero) ch.consume()
                        }
                        Log.i("aaaaaaaaaaaaaaaaaaa", "${change != null}")
                        while (change != null && change.pressed) {

                            change = awaitDragOrCancellation(change.id)
                            if (change != null && change.pressed) {
                                log = "Action Move ${change.type} ${change.position}"
                            }
                        }
                        log = "Action Up"
                    }
                }
        )
    }
}
@Composable
private fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            // 把参数应用到图层去做变幻
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // 接收变幻手势
            .transformable(state = state)
            .background(Color.Blue)
            .fillMaxSize()
    )
}

@Composable
private fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}
@Composable
private fun ScrollableSample() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Box(modifier = Modifier
        .size(150.dp)
        .scrollable(
            orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }
        )
        .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ){
        Text(offset.toString())
    }
}
//滚动嵌套
@Composable
private fun AutomaticNestedScroll() {
    val gradient = Brush.verticalGradient(0f to Color.Yellow, 1000f to Color.Red)
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(Color.LightGray)
        .verticalScroll(
            rememberScrollState()
        )
        .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(6) {
            Box(
                modifier = Modifier
                    .height(128.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "$it 滑动试试！",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .border(12.dp, Color.DarkGray)
                        .background(brush = gradient)
                        .padding(24.dp)
                        .height(150.dp)
                )
            }
        }
    }
}
//拖拽（Drag）
@Composable
private fun DraggableText() {
    var offsetX by remember { mutableStateOf(0f) }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .background(Color.LightGray)
            .padding(8.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState {
                    offsetX += it
                }
            ),
        text = "龟派气功波",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

