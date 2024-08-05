package com.liyaan.viewModel

import android.util.Log

import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.viewModelScope
import com.liyaan.base.BaseViewModel
import com.liyaan.intent.MyIntent
import com.liyaan.net.model.BannerItem
import com.liyaan.net.model.NaviJsonData
import com.liyaan.net.model.ProjectJsonListEntity
import com.liyaan.net.model.ProjectList
import com.liyaan.net.model.TreeJsonData
import com.liyaan.net.model.TreeJsonPageData

import com.liyaan.repository.UserRepository
import com.liyaan.state.ResState
import com.liyaan.state.ResStateData

import com.liyaan.state.ResStateListData

import kotlinx.coroutines.launch

class MyViewModel: BaseViewModel<UserRepository>(){
    private val _state = MutableLiveData<ResStateListData<BannerItem>>()
    val state: MutableLiveData<ResStateListData<BannerItem>> = _state

    private val _stateTreeJson = MutableLiveData<ResStateListData<TreeJsonData>>()
    val stateTreeJson: MutableLiveData<ResStateListData<TreeJsonData>> = _stateTreeJson

    private val _stateTreeListJson = MutableLiveData<ResStateData<TreeJsonPageData>>()
    val stateTreeListJson: MutableLiveData<ResStateData<TreeJsonPageData>> = _stateTreeListJson

    private val _stateNaviJson = MutableLiveData<ResStateListData<NaviJsonData>>()
    val stateNaviJson: MutableLiveData<ResStateListData<NaviJsonData>> = _stateNaviJson

    private val _stateProjectJson = MutableLiveData<ResStateListData<ProjectList>>()
    val stateProjectJson: MutableLiveData<ResStateListData<ProjectList>> = _stateProjectJson

    private val _stateProjectListJson = MutableLiveData<ResStateData<ProjectJsonListEntity>>()
    val stateProjectListJson: MutableLiveData<ResStateData<ProjectJsonListEntity>> = _stateProjectListJson


    fun processIntent() {
        launchApiCall(
            {repository.banner()}, successCallBack = {
                _state.value = ResStateListData(it.data?: emptyList(),ResState.Success)
            }, errorCallBack = {
                _state.value = ResStateListData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _state.value = ResStateListData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _state.value = ResStateListData(resState = ResState.Loading)
            }
        )
    }
    fun processTreeJson() {
        launchApiCall(
            {repository.getTreeJson()}, successCallBack = {
                _stateTreeJson.value = ResStateListData(it.data?: emptyList(),ResState.Success)
            }, errorCallBack = {
                _stateTreeJson.value = ResStateListData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _stateTreeJson.value = ResStateListData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _stateTreeJson.value = ResStateListData(resState = ResState.Loading)
            }
        )
    }
    fun processTreeListJson(page:Int,cid:String) {
        launchApiCall(
            {repository.getTreeListJson(page,cid)}, successCallBack = {
                _stateTreeListJson.value = ResStateData(it.data,ResState.Success)
            }, errorCallBack = {
                _stateTreeListJson.value = ResStateData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _stateTreeListJson.value = ResStateData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _stateTreeListJson.value = ResStateData(resState = ResState.Loading)
            }
        )
    }

    fun processNaviJson() {
        launchApiCall(
            {repository.getNaviJson()}, successCallBack = {
                _stateNaviJson.value = ResStateListData(it.data?:emptyList(),ResState.Success)
            }, errorCallBack = {
                _stateNaviJson.value = ResStateListData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _stateNaviJson.value = ResStateListData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _stateNaviJson.value = ResStateListData(resState = ResState.Loading)
            }
        )
    }


    fun processProjectJson() {
        launchApiCall(
            {repository.getProjectJson()}, successCallBack = {
                _stateProjectJson.value = ResStateListData(it.data?:emptyList(),ResState.Success)
            }, errorCallBack = {
                _stateProjectJson.value = ResStateListData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _stateProjectJson.value = ResStateListData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _stateProjectJson.value = ResStateListData(resState = ResState.Loading)
            }
        )
    }


    fun processProjectListJson(page:Int,cid:String) {
        launchApiCall(
            {repository.getProjectListJson(page, cid)}, successCallBack = {
                _stateProjectListJson.value = ResStateData(it.data,ResState.Success)
            }, errorCallBack = {
                _stateProjectListJson.value = ResStateData(resState = ResState.Fail)
            }, exceptionCallBack = {
                _stateProjectListJson.value = ResStateData(resState = ResState.Fail)
            },
            loadingCallBack = {
                _stateProjectListJson.value = ResStateData(resState = ResState.Loading)
            }
        )
    }
}