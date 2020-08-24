package com.openpix.ophttp.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openpix.logutils.LogUtils
import com.openpix.ophttp.resp.BaseModel
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.test.http.MyRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_req.setOnClickListener {
            reqUserInfo()
        }
    }
    private fun reqUserInfo() {
        LogUtils.d()
        MyRequest.getUserInfo("67", "all", object:OPResponse<BaseModel<Map<String,UserInfo>>>(){
            override fun onSuccess(t: BaseModel<Map<String, UserInfo>>) {
                LogUtils.d()
            }

            override fun onError(t: Throwable) {
                super.onError(t)
                LogUtils.e(t)
            }

        })

        MyRequest.getUserInfoString("67","all",object:OPResponse<String>() {
            override fun onSuccess(t: String) {
                t.run {
                    LogUtils.d(this)
                }
            }

            override fun onError(t: Throwable) {
                super.onError(t)
                LogUtils.e(t)
            }

        })

    }
}
