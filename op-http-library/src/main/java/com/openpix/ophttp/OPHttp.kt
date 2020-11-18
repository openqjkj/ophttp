package com.openpix.ophttp

import android.graphics.Path
import android.util.Log
import com.openpix.ophttp.callback.IRequestPreCallback
import com.openpix.ophttp.log.OPHttpLogger
import com.openpix.ophttp.retrofit.Rest
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
class OPHttp {
    private val TAG = "OPHttp"
    var okHttpClient: OkHttpClient? = null
    private var clientBuild = OkHttpClient.Builder()

    companion object {
        private var isInitLog = false
        var isOutputLog:Boolean = false
            set(value) {
                field = value
                Log.d(OPHttp::class.java.simpleName,"isOutputLog-----> " + value)
                initLogger(value)
            }

        fun initLogger(isOpen:Boolean) {
            if(isInitLog) return
            var formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("OPHttp")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
            Logger.addLogAdapter(object: AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return isOpen
                }
            })
            isInitLog = true
        }
    }

    /**
     * 签名的回调，用于给请求参数内增加sign选项
     */
    private var requestPreCallback: IRequestPreCallback?=null
    var domain:String?=null
    private var connectTimeOut :Long= 60 * 1000
    private var readTimeOut :Long= 60 * 1000

    /**
     * 网络请求头
     */
    private var header: IHttpHeader? = null

    /**
     * retrofit对象封装
     */
    private lateinit var rest: Rest

    /**
     * 设置Http请求头
     */
    fun setHeaders(httpHeader: IHttpHeader) {
        this.header = httpHeader
    }

    private fun create() {
        clientBuild.addInterceptor(AddHeaderAndParamsInterceptor(this))
        var log = HttpLoggingInterceptor(OPHttpLogger());
        log.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = clientBuild.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
            .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(log)
            .build()
        rest = Rest.ophttp(this)
    }

    /**
     * no logger selection create function.
     */
    private fun create(hasLogger:Boolean) {
        clientBuild.addInterceptor(AddHeaderAndParamsInterceptor(this))
        if(hasLogger) {
            var log = HttpLoggingInterceptor(OPHttpLogger());
            log.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = clientBuild.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(log)
                .build()
        }
        else {
            okHttpClient = clientBuild.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .build()
        }
        rest = Rest.ophttp(this)
    }

    fun <T> getRestApi(service: Class<T>): T {
        return rest?.getRestApi(service)
    }

    // 签名拦截器
    private class AddHeaderAndParamsInterceptor(private val opHttp: OPHttp): Interceptor {
        private val TAG = "ParamsInterceptor"
        override fun intercept(chain: Interceptor.Chain): Response {
            var oldRequest = chain.request()

            // 签名
            var params = HashMap<String,String>()
            // 取得所有签名key
            var paramsNames = oldRequest?.url()?.queryParameterNames()
            if(isOutputLog) {
                Log.d(TAG,"AddHeaderAndParamsInterceptor(),url:" + oldRequest?.url() + ",params:" + params.toString())
            }
            // 遍历签名，输入map
            if (null != paramsNames) {
                for (pName in paramsNames) {
                    var pValue: String = oldRequest?.url()?.queryParameter(pName) ?: continue
                    params[pName] = pValue
                    if(isOutputLog) {
                        Log.d(TAG,"requestParams:key=$pName,value:${params[pName]}")
                    }
                }
            }
            // 添加公共参数
            var signUrlBuilder
                    = oldRequest?.url()?.newBuilder()?.scheme(oldRequest?.url()?.scheme())
                ?.host(oldRequest?.url().host())
            // 请求前回调
            opHttp.requestPreCallback?.onRequestPre(params,opHttp.header?.getHeader()?:null)

            if(isOutputLog) {
                Log.d(TAG,"request:Params:$params,header:${opHttp.header?.getHeader()?:null}")
            }
            for (param in params) {
                signUrlBuilder?.setQueryParameter(param.key, param.value)
            }
            var newRequest = signUrlBuilder?.build()?.let {
                oldRequest?.newBuilder()?.method(oldRequest?.method(), oldRequest?.body())
                    ?.url(it)

            }
            // 增加公共头
            opHttp.header?.let { head->
                for((k,v) in head.getHeader()) {
                    newRequest?.addHeader(k,v)
                }
            }
            return chain.proceed(newRequest?.build())
        }
    }

    /**
     * 取消所有请求
     */
    fun cancleAllRequest() {
        okHttpClient?.dispatcher()?.cancelAll()
    }

    /**
     * OPHttp 建造器
     */
    public final class Builder {
        val opHttp = OPHttp()

        /**
         * 设置请求头
         */
        fun setHeaders(headers:IHttpHeader):Builder {
            opHttp.setHeaders(headers)
            return this
        }

        /**
         * 设置签名回调
         */
        fun setRequestPreCallbackCallback(requestPreCallback: IRequestPreCallback):Builder {
            opHttp.requestPreCallback = requestPreCallback
            return this
        }

        /**
         * 设置域名
         */
        fun domain(domain:String):Builder {
            opHttp.domain = domain
            return this
        }

        fun setConnectTimeout(connectTimeout:Long): Builder {
            opHttp.connectTimeOut = connectTimeout
            return this
        }

        fun setReadTimeout(readTimeout:Long): Builder {
            opHttp.readTimeOut = readTimeout
            return this
        }

        fun build():OPHttp {
            opHttp.create()
            return opHttp
        }

        fun build(hasLogger:Boolean):OPHttp {
            opHttp.create(hasLogger)
            return opHttp
        }
    }

}