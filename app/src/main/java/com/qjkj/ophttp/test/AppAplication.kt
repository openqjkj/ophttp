package com.qjkj.ophttp.test

import android.app.Application
import com.qjkj.ophttp.OPHttp
import com.qjkj.ophttp.callback.IRequestPreCallback
import com.qjkj.ophttp.test.http.api.PixTangApi
import com.qjkj.ophttp.test.http.HttpConfig
import com.qjkj.ophttp.test.http.MyRequest
import com.qjkj.ophttp.test.http.SignHelper

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/16 14:07
 * Version: 1.0.0
 * Description: application
 * History:
 * <author> <time> <version> <desc>
 */
class AppAplication:Application() {
    lateinit var ophttp: OPHttp
    companion object {
        lateinit var INSTANCE:AppAplication;
    }
    override fun onCreate() {
        super.onCreate()
        initHttp()
        INSTANCE = this
    }

    /**
     * 初始化http
     */
    fun initHttp() {
        var requestPreCallback = object: IRequestPreCallback {
            override fun onRequestPre(params: MutableMap<String, String>?, headers: MutableMap<String, String>?) {
                /**
                 * 这里示例处理需要在请求时添加签名的情况
                 */
                SignHelper.getSign(headers, params)
            }
        }
        ophttp = OPHttp.Builder()
            .setHeaders(HttpConfig())
            .setRequestPreCallbackCallback(requestPreCallback)
            .setConnectTimeout(60 * 1000)
            .setReadTimeout(60 * 1000)
            .domain(PixTangApi.DOMAIN).build()
        MyRequest.register(ophttp)
        OPHttp.isOutputLog = true
    }
}