package com.liyaan.net.model

data class TreeJsonData(
    val children: MutableList<TreeJsonChildren>,
    val name:String,
    val id:Int,
    val courseId:Int
)

data class TreeJsonChildren(
    val name:String,
    val courseId:Int,
    val id:Int
)
