package com.openpix.ophttp.test.http

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/15 16:08
 * Version: 1.0.0
 * Description: 签名助手类
 * History:
 * <author> <time> <version> <desc>
 */
object SignHelper {
    fun getSign(params:MutableMap<String, String>, header:MutableMap<String, String>){
        params.put("sign", "paramsign")
        header.put("sign", "headersign")
    }
}