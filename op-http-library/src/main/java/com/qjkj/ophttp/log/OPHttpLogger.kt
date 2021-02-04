package com.qjkj.ophttp.log

import com.qjkj.ophttp.utils.JsonUtil
import com.orhanobut.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-8-24
 * Version: 1.4
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
class OPHttpLogger : HttpLoggingInterceptor.Logger {
    private val mMessage = StringBuilder()
    override fun log(message: String) {
        // 请求或者响应开始
        var message = message
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0)
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (message.startsWith("{") && message.endsWith("}")
            || message.startsWith("[") && message.endsWith("]")
        ) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message))
        }
        mMessage.append("$message\n")
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Logger.d(mMessage.toString())
        }
    }
}