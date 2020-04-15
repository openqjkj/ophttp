package com.openpix.ophttp.callback

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 16:49
 * Version: 1.0.0
 * Description: 签名回调，如果不设置回调，或回调为null，则不会增加“sign”的请求参数
 * History:
 * <author> <time> <version> <desc>
 */
interface ISignCallback {
    fun onSign(params:Map<String, String>, headers:Map<String, String>):String?
}