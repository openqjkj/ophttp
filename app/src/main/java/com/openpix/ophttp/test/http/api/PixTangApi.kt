package com.openpix.ophttp.test.http.api

import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.resp.BaseModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 18:21
 * Version: 1.0.0
 * Description: 接口定义
 * History:
 */
interface PixTangApi {
    companion object {
          const val DOMAIN = "https://pixtang.com/cgi/"
    }

    /**
     * 取得用户信息
     */
    @POST("user/getUserInfo")
    fun getUserInfo(@Query("targetUid")uid:String, @Query("fields")fields:String):Single<BaseModel<Map<String, UserInfo>>>

    /**
     * 取得用户信息
     */
    @POST("user/getUserInfo")
    suspend fun getUserInfoCoroutine(@Query("targetUid")uid:String, @Query("fields")fields:String):BaseModel<Map<String, UserInfo>>


    @POST("user/getUserInfo")
    fun getUserInfoString(@Query("targetUid")uid:String, @Query("fields")fields:String):Single<String>
}