package com.openpix.ophttp.test

import android.app.Application
import com.openpix.ophttp.OPHttp
import com.openpix.ophttp.callback.IRequestPreCallback
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
    lateinit var ophttp:OPHttp
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
        var requestPreCallback = object:IRequestPreCallback {
            override fun onRequestPre(params: MutableMap<String, String>, headers: MutableMap<String, String>) {
                SignHelper.getSign(headers, params)
            }
        }
        ophttp = OPHttp.Builder().setHeaders(HttpConfig()).setSignCallback(requestPreCallback).domain(MyApi.DOMAIN).build()
        ophttp.isOuputLog = true
        MyRequest.register(ophttp)
    }
}