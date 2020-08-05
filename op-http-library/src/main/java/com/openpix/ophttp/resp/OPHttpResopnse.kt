package com.pix.http

import android.util.Log
import com.openpix.ophttp.resp.BaseModel
import io.reactivex.functions.Consumer

/**
 * Copyright (C), 2020-2020, openpix
 * Author: pix
 * Date: 2020/4/14 18:21
 * Version: 1.0.0
 * Description: RxJava形式返回
 * History:
 * <author> <time> <version> <desc>
 */
abstract class OPHttpResopnse<T> : Consumer<BaseModel<T>> {
    val TAG = "OPHttpResopnse"

    protected abstract fun onSuccess(t: T?)

    protected abstract fun onFailed(code: Int?, msg: String?)

    override fun accept(t: BaseModel<T>?) {
        if (t?.state == 0) {
            onSuccess(t?.content)
        } else {
            onFailed(t?.state, t?.message)
        }
    }
}