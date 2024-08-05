package com.liyaan.mycompose.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.liyaan.mycompose.Page



@Composable
fun MainBottomPage(
    navController:NavHostController,
    itemes:List<Page>,
    modifier: Modifier = Modifier,
    elevation:Dp = 0.dp,
    backgroundColor:Color = Color.Transparent,
    contentColor:Color =Color.Transparent,
    cutoutShape: Shape? = null,
    contentPadding: PaddingValues = AppBarDefaults.ContentPadding,){

    BottomAppBar(
        modifier = modifier,
        elevation = elevation,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        cutoutShape = cutoutShape,
        contentPadding = contentPadding
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        var isSelected: Boolean
        repeat(itemes.size){screenPage ->
            isSelected = currentDestination?.hierarchy?.any{
                it.route == itemes[screenPage].route
            } == true
            if (itemes[screenPage].isShowFloatButton){
                Spacer(modifier = Modifier.weight(1f,true))
            }else{
                BottomNavigationItem(
                    selected = isSelected,
                    selectedContentColor = Color(0xFF037FF5),
                    unselectedContentColor = Color(0xFF31373D),
                    icon = {
                        Image(
                            painter = if (isSelected) painterResource(id = itemes[screenPage].iconSelect) else painterResource(id = itemes[screenPage].iconUnselect),
                            contentDescription =null,
                            modifier =  Modifier.size(25.dp),
                            contentScale = ContentScale.Crop
                        )
                    },
//                    alwaysShowLabel = itemes[screenPage].isShowText,
                    label = {
                        Text(
                            text = stringResource(id = itemes[screenPage].resId),
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected){
                                    Color.Yellow
                                }else{
                                    Color.Black
                                }
                            )
                        )
                    },
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        navController.navigate(itemes[screenPage].route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
//                            Log.i("aaaaaaaaaaaaa", itemes[screenPage].route)
//                            Log.i("aaaaaaaaaaaaa",Page.Home.route)
//                            popUpTo(Page.Home.route){
//                                inclusive = true
//                            }
//                            popUpTo(Page.ClassList.route){
//                                inclusive = true
//                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    },

                    )
            }
        }
    }
}

