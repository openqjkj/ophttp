package com.openpix.ophttp.resp

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 16:42
 * Version: 1.0.0
 * Description: 返回数据，最外层的json格式
 * 范例
 *      {
 *        "state":0
 *        "message":"success"
 *        "message": {
 *             "name":"zhangsan"
 *             "age:18
 *        }
 * History:
 * <author> <time> <version> <desc>
 */
class BaseModel<T> {
    var state: Int = 0
    var message: String? = null
    var content: T? = null
}
