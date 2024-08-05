package com.liyaan.repository

import com.liyaan.net.BaseRepository
import com.liyaan.net.model.BannerItem
import com.liyaan.net.model.NaviJsonData
import com.liyaan.net.model.ProjectJsonListEntity
import com.liyaan.net.model.ProjectList
import com.liyaan.net.model.TreeJsonData
import com.liyaan.net.model.TreeJsonPageData
import com.liyaan.state.StateModelEntity
import retrofit2.http.Path
import retrofit2.http.Query

class UserRepository: BaseRepository() {

    suspend fun banner(): StateModelEntity<List<BannerItem>?> {
        return apiCall {
            apiService.getBanner()
        }
    }
    suspend fun getTreeJson(): StateModelEntity<List<TreeJsonData>?> {
        return apiCall {
            apiService.getTreeJson()
        }
    }
    suspend fun getTreeListJson(page:Int,cid:String): StateModelEntity<TreeJsonPageData?> {
        return apiCall {
            apiService.getTreeListJson(page,cid)
        }
    }
    suspend fun getNaviJson(): StateModelEntity<List<NaviJsonData>?> {
        return apiCall {
            apiService.getNaviJson()
        }
    }
    suspend fun getProjectJson(): StateModelEntity<List<ProjectList>?> {
        return apiCall {
            apiService.getProjectJson()
        }
    }
    suspend fun getProjectListJson(page:Int,cid:String): StateModelEntity<ProjectJsonListEntity?> {
        return apiCall {
            apiService.getProjectListJson(page, cid)
        }
    }
}