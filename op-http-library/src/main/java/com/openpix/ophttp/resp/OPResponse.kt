package com.openpix.ophttp.resp

import androidx.annotation.NonNull
import java.net.ConnectException

import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException


/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 18:21
 * Version: 1.0.0
 * Description: RxJava形式返回
 * History:
 * <author> <time> <version> <desc>
 */
abstract class OPResponse<T> : SingleObserver<T> {

    open fun onFailed(code: Int, msg: String?) {}

    override fun onSubscribe(@NonNull d: Disposable) {
        // 比如显示加载中对话框
    }

    override fun onError(t: Throwable) {
        if (t is ConnectException) {
            //网络连接失败
            onFailed(403, t.message)
        } else if (t is HttpException) {
            onFailed(t.code(), t.message())
        } else {
            onFailed(405, t.message)
        }
    }
}