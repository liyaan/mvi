package com.liyaan.mycompose.view

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SootheBottomNavigation(
    modifier:Modifier = Modifier,
    unicons: Array<ImageVector>,
    icons: Array<ImageVector>,
    titles: Array<String>,
    onClickSelectIndex:(Int)->Unit
){
    var index = remember{ mutableStateOf(0) }
    BottomNavigation(
        elevation = 0.dp,
        modifier= modifier,
        backgroundColor = MaterialTheme.colors.background) {
            repeat(icons.size){
                BottomNavigationItem(selected = index.value==it, icon = {
                    Icon(
                        imageVector  = if (index.value==it) icons[it] else unicons[it],
                        contentDescription = null,
                    )
                }, onClick = {
                    index.value = it
                    onClickSelectIndex(index.value)
                },label = {
                    Text(titles[it])
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Red)
            }
    }
}