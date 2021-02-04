package com.qjkj.ophttp.ext

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-9-9
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io())
        .delay(withDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())

