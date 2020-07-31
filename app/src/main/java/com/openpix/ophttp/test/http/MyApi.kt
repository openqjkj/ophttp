package com.openpix.ophttp.test.http

import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.resp.BaseModel
import io.reactivex.Observable
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
interface MyApi {
    companion object {
          const val DOMAIN = "https://pixtang.com/cgi/"
    }

    /**
     * 取得用户信息
     */
    @POST("user/getUserInfo")
    fun getUserInfo(@Query("targetUid")uid:String, @Query("fields")fields:String):Observable<BaseModel<Map<String, UserInfo>>>
}