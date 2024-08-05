package com.liyaan.net.model

import java.io.Serializable

data class ProjectJsonListEntity(
    val curPage:Int,
    val offset:Int,
    val pageCount:Int,
    val size:Int,
    val total:Int,
    val datas:MutableList<ProjectJsonListData>
)

data class ProjectJsonListData(
    val chapterName:String,
    val chapterId:Int,
    val id:Int,
    val link:String,
    val niceShareDate:String,
    val niceDate:String,
    val title:String,
    val desc:String,
    val envelopePic:String,
    val projectLink:String,
    val author:String,
    val superChapterId:Int,
    val superChapterName:String
):Serializable

