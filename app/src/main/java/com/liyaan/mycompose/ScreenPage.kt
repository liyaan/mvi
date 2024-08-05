package com.liyaan.mycompose

import androidx.annotation.StringRes

sealed class Page(
    val route:String,
    @StringRes
    val resId:Int = 0,
    var iconSelect: Int = 0,
    var iconUnselect: Int= 0,
    var isShowFloatButton:Boolean = false
){
    object Home:Page(
        route = "Home",
        resId = R.string.home,
        iconSelect = R.mipmap.tab_home,
        iconUnselect = R.mipmap.tab_home_1
    )
    object ClassList:Page(
        route = "ClassList",
        resId = R.string.ClassList,
        iconSelect = R.mipmap.tab_myclass,
        iconUnselect = R.mipmap.tab_myclass_1
    )
    object CenterIcon:Page(
        route = "CenterIcon",
        isShowFloatButton = true
    )
    object StudyCenter:Page(
        route = "StudyCenter",
        resId = R.string.StudyCenter,
        iconSelect = R.mipmap.tab_xielie,
        iconUnselect = R.mipmap.tab_xielie_1
    )
    object OneCenter:Page(
        route = "OneCenter",
        resId = R.string.OneCenter,
        iconSelect = R.mipmap.tab_my,
        iconUnselect = R.mipmap.tab_my_1
    )
}
