package com.liyaan.net

import com.liyaan.net.model.UserApi
import com.liyaan.state.StateModelEntity

open class BaseRepository {
    companion object{
        val apiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkModule.getService(
                UserApi::class.java
            )
        }
    }

    suspend fun <T> apiCall(api: suspend () -> StateModelEntity<T>): StateModelEntity<T> {
        return api.invoke()
    }
}