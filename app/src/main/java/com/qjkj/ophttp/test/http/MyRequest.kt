package com.qjkj.ophttp.test.http

import com.openpix.logutils.LogUtils
import com.qjkj.ophttp.OPHttp
import com.qjkj.ophttp.ext.async
import com.qjkj.ophttp.resp.BaseModel
import com.qjkj.ophttp.resp.OPResponse
import com.qjkj.ophttp.retrofit.Rest
import com.qjkj.ophttp.test.http.api.PixTangApi
import com.qjkj.ophttp.test.bean.UserInfo

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
    var opHttp: OPHttp? = null

    fun register(opHttp: OPHttp?) {
        MyRequest.opHttp = opHttp
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