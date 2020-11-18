package com.openpix.ophttp.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.openpix.logutils.LogUtils
import com.openpix.ophttp.IHttpHeader
import com.openpix.ophttp.resp.BaseModel
import com.openpix.ophttp.resp.OPResponse
import com.openpix.ophttp.test.bean.UserInfo
import com.openpix.ophttp.test.http.MyRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val mainScope by lazy { MainScope() }
    var contentData: LiveData<String> =  liveData {  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_req.setOnClickListener {
            reqUserInfo()
        }
    }
    private fun reqUserInfo() {
        reqJson()
//        reqString()
        reqReplaceHead()

    }

    // 测试Gson自动解析
    private fun reqJson() {
        MyRequest.getUserInfo("67", "all", object:OPResponse<BaseModel<Map<String,UserInfo>>>(){
            override fun onSuccess(t: BaseModel<Map<String, UserInfo>>) {
                LogUtils.d()
            }

            override fun onError(t: Throwable) {
                super.onError(t)
                LogUtils.e(t)
            }

        })
    }
    // 测试字符串返回
    private fun reqString() {
        // 测试按字符串解析
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

    /**
     * 测试动态替换请求头
     */
    private fun reqReplaceHead() {
        MyRequest.opHttp?.setHeaders(object:IHttpHeader {
            override fun getHeader(): MutableMap<String, String> {
                var map = HashMap<String,String>()
                map.put("replaceK1" , "replaceV1")
                map.put("replaceK2" , "replaceV2")
                return map
            }

        })
        MyRequest.getUserInfo("67", "all", object:OPResponse<BaseModel<Map<String,UserInfo>>>(){
            override fun onSuccess(t: BaseModel<Map<String, UserInfo>>) {
                LogUtils.d()
            }

            override fun onError(t: Throwable) {
                super.onError(t)
                LogUtils.e(t)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
