package com.openpix.ophttp.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openpix.logutils.LogUtils
import com.openpix.ophttp.callback.ISignCallback
import com.openpix.ophttp.OPHttp
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.test.http.HttpConfig
import com.openpix.ophttp.test.http.MyApi
import com.openpix.ophttp.test.http.MyRequest
import com.openpix.ophttp.test.http.SignHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initHttp()
        reqUserInfo()
    }

    private fun initHttp() {
        OPHttp.setHeaders(HttpConfig())
        OPHttp.domain=MyApi.DOMAIN
        OPHttp.signCallback = object: ISignCallback {
            override fun onSign(
                params: Map<String, String>,
                headers: Map<String, String>
            ): String? {
                return SignHelper.getSing(params, headers)
            }
        }
    }

    private fun reqUserInfo() {
        LogUtils.d()
        MyRequest.getUserInfo("898210", "all", object:OPResponse<Map<String, UserInfo>>() {
            override fun onSuccess(t: Map<String, UserInfo>?) {
                LogUtils.d(t?.keys.toString() + "===")
            }

            override fun onFailed(code: Int, msg: String?) {
                super.onFailed(code, msg)
            }

        })

    }
}
