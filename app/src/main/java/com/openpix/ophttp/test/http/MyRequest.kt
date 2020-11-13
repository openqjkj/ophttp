package com.openpix.ophttp.test.http

import com.openpix.logutils.LogUtils
import com.openpix.ophttp.OPHttp
import com.openpix.ophttp.ext.async
import com.openpix.ophttp.resp.BaseModel
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.retrofit.Rest
import com.openpix.ophttp.test.http.api.PixTangApi

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 19:28
 * Version: 1.0.0
 * Description: 我的请求类
 * History:
 * <author> <time> <version> <desc>
 */
object MyRequest {
    var opHttp:OPHttp? = null

    fun register(opHttp: OPHttp?) {
        this.opHttp = opHttp
    }
    /**
     * 取得用户信息
     * @param httpResponse
     */
    fun getUserInfo(
        uid: String,
        fields:String, httpResponse: OPResponse<BaseModel<Map<String, UserInfo>>>
    ) {
        Rest.ophttp(opHttp).getRestApi(PixTangApi::class.java).getUserInfo(uid,fields)
            .map {
                LogUtils.d("map...........")
                it
            }.async().subscribe(httpResponse)
    }

    fun getUserInfoString(uid:String, filelds:String, httpResponse: OPResponse<String>) {
        Rest.ophttp(opHttp).getRestApi(PixTangApi::class.java).getUserInfoString(uid,filelds)
            .async()
            .subscribe(httpResponse)

    }
}