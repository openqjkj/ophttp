package com.openpix.ophttp

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 16:42
 * Version: 1.0.0
 * Description: http header 配置
 * History:
 * <author> <time> <version> <desc>
 */
interface IHttpHeader {
    fun getHeader():Map<String, String>
}