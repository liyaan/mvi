package com.liyaan.net.model


import com.liyaan.state.StateModelEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    /**
     * 获取用户信息
     * */
    @GET("banner/json")
    suspend fun getBanner(): StateModelEntity<List<BannerItem>?>

    @GET("tree/json")
    suspend fun getTreeJson(): StateModelEntity<List<TreeJsonData>?>

    @GET("article/list/{page}/json")
    suspend fun getTreeListJson(@Path("page")page:Int,@Query("cid")cid:String): StateModelEntity<TreeJsonPageData?>

    @GET("navi/json")
    suspend fun getNaviJson(): StateModelEntity<List<NaviJsonData>?>

    @GET("project/tree/json")
    suspend fun getProjectJson(): StateModelEntity<List<ProjectList>?>


    @GET("project/list/{page}/json")
    suspend fun getProjectListJson(@Path("page")page:Int,@Query("cid")cid:String)
        : StateModelEntity<ProjectJsonListEntity?>
}