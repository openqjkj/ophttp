package com.openpix.ophttp.test.http

import com.openpix.ophttp.OPHttp
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.retrofit.Rest
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        fields:String, httpResponse: OPResponse<Map<String, UserInfo>>
    ) {
        Rest.ophttp(opHttp).getRestApi(MyApi::class.java).getUserInfo(uid,fields)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(httpResponse)
    }
}