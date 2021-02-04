package com.qjkj.ophttp.test.http.api

import com.qjkj.ophttp.resp.BaseModel
import com.qjkj.ophttp.test.bean.UserInfo
import io.reactivex.Single
import retrofit2.http.Multipart
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
          const val DOMAIN = "https://pixtang.com/"
    }

    /**
     * 取得用户信息
     */
    @POST("/cgi/user/getUserInfo")
    fun getUserInfo(@Query("targetUid")uid:String, @Query("fields")fields:String):Single<BaseModel<Map<String, UserInfo>>>

    /**
     * 取得用户信息
     */
    @POST("/cgi/user/getUserInfo")
    suspend fun getUserInfoCoroutine(@Query("targetUid")uid:String, @Query("fields")fields:String): BaseModel<Map<String, UserInfo>>

    @POST("/cgi/user/getUserInfo")
    fun getUserInfoString(@Query("targetUid")uid:String, @Query("fields")fields:String):Single<String>

    @Multipart
    @POST("/upload_img")
    fun uploadImg()


}