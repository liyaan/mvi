package com.liyaan.mycompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liyaan.mycompose.ui.theme.MyComposeTheme
import kotlinx.coroutines.delay

class AnimatingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                LaunchAnimation()
            }
        }
    }
}

//颜色切换
@Composable
fun AnimationBox(modifier:Modifier = Modifier.fillMaxSize()){
    var boxState by remember {
        mutableStateOf(BoxState.Expanded)
    }
    val transitionData = updateTransitionData(boxState = boxState)
    Box(
        modifier = modifier
            .background(transitionData.color)
            .size(transitionData.size)
            .clickable {
                boxState =
                    if (boxState == BoxState.Expanded) BoxState.Collapsed else BoxState.Expanded
            }
    ){

    }
}
//大小和背景颜色切换
@Composable
fun AnimatingColumn(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var boxState by remember { mutableStateOf(BoxState.Collapsed) }

    LaunchedEffect(Unit) {
        boxState = BoxState.Expanded
    }

    val transitionData = updateTransitionData(boxState)
    Column(
        modifier = Modifier
            .background(transitionData.color)
            .size(transitionData.size)
            .clickable {
                boxState =
                    if (boxState == BoxState.Expanded) BoxState.Collapsed else BoxState.Expanded
            }
    ) {
        Text("Apply to a column")
    }
}

enum class BoxState { Collapsed, Expanded }

private class TransitionData(color: State<Color>,
                             size: State<Dp>){
    val color by color
    val size by size
}
@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState, label = "box state")
    val color = transition.animateColor(label = "color") { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp(label = "size") { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}

@Composable
fun AnimatedVisibilityView(){
    var visible by remember {
        mutableStateOf(true)
    }
   Box {
       AnimatedVisibility(visible){
           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .height(200.dp)
                   .clip(RoundedCornerShape(8.dp))
                   .background(
                       Color.Blue
                   )
           )
       }
       Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
           visible = !visible
       }) {
           Text("Toggle Show/Hide")
       }
   }
}


@Composable
fun AnimatedVisibilityOneView(){
    var visible by remember {
        mutableStateOf(true)
    }
    val density = LocalDensity.current
    Box {
        AnimatedVisibility(visible, enter = slideInVertically {
            with(density){-40.dp.roundToPx()}
        } + expandVertically(
            expandFrom = Alignment.Top
        )+ fadeIn(
            initialAlpha = 0.3f
        ), exit = slideOutVertically() + shrinkVertically() + fadeOut()){
            Text("降Compose十八掌",
                Modifier
                    .fillMaxWidth()
                    .height(200.dp))
        }
        Button(modifier = Modifier.align(Alignment.BottomCenter), onClick = {
            visible = !visible
        }) {
            Text("Toggle Show/Hide")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomForChildren(modifier: Modifier = Modifier.fillMaxSize()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var visible by remember { mutableStateOf(true) }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)){
                Box(modifier = Modifier
                    .align(Alignment.Center)
                    .animateEnterExit(
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    )
                    .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                    .background(Color.Magenta)
                ){
                    Text(
                        text = "你会看到不同的风景！",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Button(
            onClick = { visible = !visible },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("点击有惊喜！！！")
        }
    }
}
@Composable
fun CrossfadeDemo(modifier: Modifier = Modifier.fillMaxSize()) {
    var done by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(5000)
        done = true
    }
    Crossfade(
        modifier = modifier,
        targetState = !done,
        label = "crossfade"
    ) { loading ->

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = if (loading) Alignment.Center else Alignment.TopStart
        ) {
            if (loading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(Modifier.size(66.dp))
                    Text(
                        text = "玩命加载中...",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            } else {
                Text(
                    text =
                    """
                       “降龙十八掌可说是【武学中的巅峰绝诣】，当真是无坚不摧、无固不破。虽招数有限，但每一招均具绝大威力。
                        北宋年间，丐帮帮主萧峰以此邀斗天下英雄，极少有人能挡得他三招两式，气盖当世，群豪束手。
                        当时共有“降龙廿八掌”，后经萧峰及他义弟虚竹子删繁就简，取精用宏，改为降龙十八掌，掌力更厚。
                        这掌法传到洪七公手上，在华山绝顶与王重阳、黄药师等人论剑时施展出来，王重阳等尽皆称道。”
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

enum class UiState{
    Loading,Loaded,Error
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentView(
    LoadingScreen:@Composable ()->Unit,
    LoadedScreen:@Composable ()->Unit,
    ErrorScreen:@Composable ()->Unit
){
    var state by remember {
        mutableStateOf(UiState.Loading)
    }
    AnimatedContent(
        targetState = state,
        transitionSpec = { fadeIn(tween(1500)) with
                fadeOut(tween(1500, delayMillis = 1500)) },
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            state = when (state) {
                UiState.Loading -> UiState.Loaded
                UiState.Loaded -> UiState.Error
                UiState.Error -> UiState.Loading
            }
            Log.i("aaaaaaaaaaaaa",state.name)
        },
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            UiState.Loading -> {
                LoadingScreen()
            }
            UiState.Loaded -> {
                LoadedScreen()
            }
            UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun AnimatedContentSView(){
    AnimatedContentView(LoadingScreen = {
            CircularProgressIndicator()                            
    }, LoadedScreen = { 
        Image(painter = painterResource(id = R.mipmap.gongkaiclass_icon), contentDescription ="" )
    }) {
        
    }
}

@Composable
fun AnimationHeight(){
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .background(Color.Blue)
            .animateContentSize()
            .height(if (expanded) 400.dp else 200.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
            }
        )
        Box(modifier = Modifier
            .background(Color.Red)
            .height(100.dp)
            .fillMaxWidth())
    }

}

@Composable
fun PropertyAnimation(modifier: Modifier = Modifier.fillMaxSize()) {
    var showing by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (showing) 0f else 1f, label = "property")

    val alpha by animateFloatAsState(targetValue = if (showing) 0f else 1f, label = "property")
    
    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "大家好 生活美好", modifier = Modifier
            .padding(20.dp)
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            },style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(50.dp))
        Button(onClick = { showing = !showing}) {
            Text("点一下")
        }
    }
}
/*
* animateColorAsState
*
* 用于颜色值变化，指定两个值后，会在中间进行的插值作为动画的帧，能让颜色变化更为平滑细腻
*
* animateIntOffsetAsState
*
* 用于把值Offset的变化变成动画，适合于使用Offset，如Modifier.offset，Modifier.layout等。
*
* animateDpAsState
*
* 把类型为Dp的值变为动画，适合所有能使用Dp作为参数值的地方，如padding，shadowElavation等。
* */


@Composable
fun LaunchAnimation(modifier: Modifier = Modifier.fillMaxSize()) {
    val alphaAnimation = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000)
        )
    }
    Box(
        modifier = Modifier
            .offset(16.dp, 16.dp)
            .size(200.dp)
            .graphicsLayer {
                alpha = alphaAnimation.value
            }
            .background(Color.Magenta)
    )
}