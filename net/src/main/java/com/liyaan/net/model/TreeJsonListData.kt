package com.liyaan.net.model

data class TreeJsonPageData(
    val curPage:Int,
    val offset:Int,
    val pageCount:Int,
    val size:Int,
    val total:Int,
    val datas:MutableList<TreeJsonListData>
)

data class TreeJsonListData(
    val chapterName:String,
    val id:Int,
    val link:String,
    val niceShareDate:String,
    val niceDate:String,
    val title:String
)

