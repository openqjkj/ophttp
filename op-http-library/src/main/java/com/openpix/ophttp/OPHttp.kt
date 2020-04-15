package com.openpix.ophttp

import android.util.Log
import com.openpix.ophttp.callback.ISignCallback
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 16:29
 * Version: 1.0.0
 * Description: ophttp使用总入口
 * History:
 * <author> <time> <version> <desc>
 */
object OPHttp {
    private val TAG = "OPHttp"
    var okHttpClient: OkHttpClient? = null
    private var clientBuild = OkHttpClient.Builder()

    /**
     * 签名的回调，用于给请求参数内增加sign选项
     */
    var signCallback: ISignCallback?=null
    var domain:String?=null

    /**
     * 设置Http请求头
     */
    fun setHeaders(httpHeader: IHttpHeader) {
        clientBuild.addInterceptor(AddHeaderAndParamsInterceptor(httpHeader.getHeader()))
        okHttpClient = clientBuild.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
            .build()
    }

    // 签名拦截器
    private class AddHeaderAndParamsInterceptor(var headers:Map<String,String>): Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var oldRequest = chain?.request()

            // 签名
            var params = HashMap<String,String>()
            // 取得所有签名key
            var paramsNames = oldRequest?.url()?.queryParameterNames()
            Log.d(TAG,"AddHeaderAndParamsInterceptor(),url:" + oldRequest?.url() + ",params:" + params.toString())
            // 遍历签名，输入map
            if (null != paramsNames) {
                for (pName in paramsNames) {
                    var pValue: String = oldRequest?.url()?.queryParameter(pName) ?: continue
                    params[pName] = pValue
                    Log.d(TAG,"requestParams:key=$pName,value:${params[pName]}")
                }
            }
            // 添加公共参数
            var signUrlBuilder
                    = oldRequest?.url()?.newBuilder()?.scheme(oldRequest?.url()?.scheme())
                ?.host(oldRequest?.url().host())
                ?.addQueryParameter("r", genRandomString())
            var sign= OPHttp.signCallback?.onSign(params, headers)
            if(null != OPHttp.signCallback && null != sign) {
                signUrlBuilder = signUrlBuilder?.addQueryParameter("sign",sign)
            }
            var newRequest = oldRequest?.newBuilder()?.method(oldRequest?.method(),oldRequest?.body())
                ?.url(signUrlBuilder?.build())
            // 增加公共头
            for((k,v) in headers) {
                newRequest?.addHeader(k,v)
            }
            return chain?.proceed(newRequest?.build())!!
        }
    }

    fun cancleRequest() {
        okHttpClient?.dispatcher()?.cancelAll()
    }

    //生成随机字符串
    private fun genRandomString(): String {
        val random = Random(System.currentTimeMillis())
        var rand: Int

        var len = random.nextInt(10)
        if (len < 4) {
            len = 4
        }
        val buf = StringBuilder()
        for (i in 0 until len) {
            rand = random.nextInt(26)
            buf.append((rand + 'a'.toInt()).toChar())
        }
        return buf.toString()
    }
}