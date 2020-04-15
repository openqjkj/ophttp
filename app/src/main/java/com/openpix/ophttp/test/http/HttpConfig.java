package com.openpix.ophttp.test.http;

import com.openpix.ophttp.IHttpHeader;

import java.util.HashMap;

import okhttp3.Credentials;


/**
 * 实现Http网络请求的参数配置
 */
public class HttpConfig implements IHttpHeader {
	private static final String REFERER = "http://tiantian.qq.com";

	@Override
	public HashMap<String, String> getHeader() {
		HashMap<String, String> headers = new HashMap<String, String>();
		String MOBILE = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;// 操作系统版本
		if (MOBILE.length() > 20) { //服务器数据库表字段设置最多20个字符
			MOBILE = MOBILE.substring(0, 20);
		}
		String SV = android.os.Build.VERSION.RELEASE;// 操作系统版本
		headers.put("sy", SV);// 系统版本 如：2.0，2.2，2.2.1，3.1，3.1.1，3.1.2
		headers.put("mobile", MOBILE);// 设备型号
		headers.put("Referer", REFERER);
		headers.put("signtype",2 + "");
		long timestamp = System.currentTimeMillis();
		headers.put("ts",timestamp + "");
		headers.put("cleartext", +timestamp + ""); //明文校验
		headers.put("Authorization", Credentials.basic("user", "123456"));
		return headers;
	}
}
