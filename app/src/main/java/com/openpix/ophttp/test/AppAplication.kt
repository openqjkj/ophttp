package com.openpix.ophttp.test

import android.app.Application
import com.openpix.ophttp.OPHttp
import com.openpix.ophttp.callback.ISignCallback
import com.openpix.ophttp.test.http.HttpConfig
import com.openpix.ophttp.test.http.MyApi
import com.openpix.ophttp.test.http.MyRequest
import com.openpix.ophttp.test.http.SignHelper

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
    override fun onCreate() {
        super.onCreate()
        initHttp()
    }

    /**
     * 初始化http
     */
    fun initHttp() {
        var signCallback = object:ISignCallback {
            override fun onSign(params: Map<String, String>, headers: Map<String, String>): String? {
                return SignHelper.getSign(headers, params)
            }
        }
        var ophttp = OPHttp.Builder().setHeaders(HttpConfig()).setSignCallback(signCallback).domain(MyApi.DOMAIN).build()
        MyRequest.register(ophttp)
    }
}