package com.liyaan.mycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.liyaan.mycompose.one.MyOwnColumn
import com.liyaan.mycompose.one.StaggeredGrid
import com.liyaan.mycompose.ui.theme.MyComposeTheme



class OneActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                Show()
            }
        }
    }
}
 @Composable
private fun BodyContent(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}
//使用
val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social", "Technology", "TV", "Writing"
)
@Composable
fun Show() {
    StaggeredGrid(
        modifier =Modifier.horizontalScroll(rememberScrollState())
    ) {
        for (topic in topics) {
            Text(
                text = topic,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray)
            )
        }
    }
}