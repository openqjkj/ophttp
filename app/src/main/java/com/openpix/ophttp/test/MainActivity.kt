package com.openpix.ophttp.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openpix.logutils.LogUtils
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.test.http.MyRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqUserInfo()
    }
    private fun reqUserInfo() {
        MyRequest.getUserInfo("67", "all", object:OPResponse<Map<String, UserInfo>>() {
            override fun onSuccess(t: Map<String, UserInfo>?) {
                LogUtils.d("t:" + t)
            }

            override fun onFailed(code: Int, msg: String?) {
                super.onFailed(code, msg)
                LogUtils.d("msg:" + msg)
            }
        })
    }
}
