package com.liyaan.net.model

data class NaviJsonData(
    val articles:MutableList<NaviListJsonData>,
    val cid:Int,
    val name:String
)
data class NaviListJsonData(
    val link:String,
    val title:String,
    val chapterName:String,
    val author:String,
    val id:Int,
    val niceDate:String,
    val courseId:Int,
    val chapterId:Int
)
