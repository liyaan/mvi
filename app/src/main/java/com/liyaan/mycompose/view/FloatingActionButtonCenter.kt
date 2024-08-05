package com.liyaan.mycompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionButtonAdd(onAddClick:()->Unit){
    FloatingActionButton(onClick = onAddClick, modifier = Modifier.offset(0.dp,55.dp).background(
        Color.White, shape = CircleShape
    ).padding(5.dp),

    ) {
        Box(){
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}