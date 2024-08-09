package com.liyaan.mycompose.one

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import java.util.Collections.max
import kotlin.math.max

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,  //用于外部修饰自己
    rows: Int = 5,  //默认显示三行
    content: @Composable () -> Unit //接收子元素
){
    Layout(
        modifier = modifier,
        content = content
    ){ measurables, constraints ->
        val rowWidths = IntArray(rows){0} //记录每一行的宽度
        val rowHeights = IntArray(rows){0} //记录每一行的高度
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            //根据索引对子元素分组，记录每一行的高度
            val row = index%rows
            rowWidths[row] += placeable.width    //一行的宽度=这行所有元素宽度之和
            rowHeights[row] = max(rowHeights[row], placeable.height)    //一行的高度=这行最高的元素
            placeable   //测量完要返回placeable对象
        }
        //【第二步：计算自身的尺寸】
        val width = rowWidths.maxOrNull()   //宽度取所有行中宽度最大值
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))  //宽度限制在最大值和最小值之间
            ?: constraints.minWidth //为null就设为最小值
        val height = rowHeights.sumOf { it }    //高度为所有行高之和
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
        val rowY = IntArray(rows){0}    //每行子元素在y轴上摆放的坐标
        for (i in 1 until rows) {    //第一行肯定是0，从第二行开始赋值
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]    //当前行y坐标=前一行y坐标+前一行高度
        }
        //【第三步：摆放子元素】
        layout(width, height) {    //在自身的尺寸里摆放
            val rowX = IntArray(rows){0}    //每行子元素在x轴上的坐标
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(rowX[row], rowY[row])
                rowX[row] += placeable.width
            }
        }

    }
}