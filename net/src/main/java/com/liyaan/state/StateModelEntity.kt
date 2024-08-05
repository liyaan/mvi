package com.liyaan.state

data class StateModelEntity<T> (
    val errorMsg: String?,
    val data: T?,
    val errorCode: Int,
    val url: String?
)