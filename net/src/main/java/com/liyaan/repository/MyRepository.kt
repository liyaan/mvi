package com.liyaan.repository

import android.util.Log
import kotlinx.coroutines.delay

class MyRepository {
    suspend fun fetchData(): List<Any> {
        // 网络请求逻辑
        delay(1000) // 模拟网络延迟
        Log.i("aaaaaaaaaaaaaaaa","vvvvvvvvvvvvvvvvv")
        return listOf<Any>() // 模拟数据返回
    }
}