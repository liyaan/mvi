package com.liyaan.state

sealed class ResState {
    object Success : ResState()
    object Fail : ResState()
    object Loading : ResState()
    object InitRes : ResState()
}

data class ResStateListData<T>(
    val beanList: List<T> = emptyList(),
    var resState: ResState =ResState.InitRes
)
data class ResStateData<T>(
    val beanEntity: T? = null,
    val resState: ResState =ResState.InitRes
)

