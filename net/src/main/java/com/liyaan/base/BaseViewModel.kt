package com.liyaan.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.liyaan.net.BaseRepository
import com.liyaan.net.Net
import com.liyaan.state.StateModelEntity
import com.liyaan.utils.GenericsUtil

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author: chinadragon
 *Time: 2024/5/27
 */
open class BaseViewModel<Repository : BaseRepository> :
    ViewModel() {


    private val jobList = mutableListOf<Job>()

    val repository: Repository by lazy {
        return@lazy GenericsUtil.getInstant(this)
    }

    fun <T> launchApiCall(
        apiCall: suspend CoroutineScope.() -> StateModelEntity<T>,
        successCallBack: suspend CoroutineScope.(response: StateModelEntity<T>) -> Unit = {},
        errorCallBack: suspend CoroutineScope.(response: StateModelEntity<T>) -> Unit = {},
        exceptionCallBack: suspend CoroutineScope.() -> Unit = {},
        loadingCallBack: suspend CoroutineScope.() -> Unit = {},
        showToast: Boolean = true,
    ) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main){
                    loadingCallBack()
                }
                val response = apiCall()
                withContext(Dispatchers.Main) {
                    if (response.errorCode == Net.NET_SUCCESS) {
                        successCallBack(response)

                    } else {
                        errorCallBack(response)
                        if (showToast) {
                            response.errorMsg?.let { Log.i("BaseModelView", it) }
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    exceptionCallBack()
                }

            }
        }

        if (job.isActive) {
            jobList.add(job)
        }
    }

    //演示使用rxjava3
    protected var listDisposable: MutableList<Disposable> = mutableListOf()
    protected open fun addDisposable(disposable: Disposable) {
        listDisposable.add(disposable)
    }

    //演示使用rxjava3
//    protected open fun <T> wrapObservable(observable: Observable<StateModelEntity<T?>>): Observable<StateModelEntity<T?>> {
//        return observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//    }

    private fun cancelAllJob() {

        if (listDisposable.isNotEmpty()) {
            listDisposable.forEach {
                if (!it.isDisposed) {
                    it.dispose()
                }
            }
            listDisposable.clear()
        }

        if (jobList.isEmpty()) {
            return
        }

        jobList.forEach {
            if (it.isActive) {
                it.cancel()
            }
        }
        jobList.clear()
    }

    override fun onCleared() {
        cancelAllJob()
        super.onCleared()
    }
}