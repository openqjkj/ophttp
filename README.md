# ophttp
http库封装

# ophttp库引入

```gradle
    implementation 'com.openpix:ophttp:1.0.0'
```

# 请求接口定义

范例:`myApi.kt`

```kotlin
interface MyApi {
    companion object {
          const val DOMAIN = "http://pixtang.com/cgi/"
    }

    /**
     * 取得用户信息
     */
    @POST("user/getUserInfo")
    fun getUserInfo(@Query("targetUid")uid:String, @Query("fields")fields:String):Observable<BaseModel<Map<String, UserInfo>>>
}
```

域名的定义`DOMAIN`

请求接口定义: `getUserInfo()`

# 请求头定义

请求头定义需要继承自`IhttpHeader接口

范例:`HttpConfig.kt`

```kotlin

/**
 * 实现Http网络请求的参数配置
 */
public class HttpConfig implements IHttpHeader {

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
		long timestamp = System.currentTimeMillis();
		headers.put("ts",timestamp + "");
		headers.put("Authorization", Credentials.basic("user", "123456"));
		return headers;
	}
}
```

如有需要在请求前对参数和请求头进行处理，初始化时设置`IRequestPreCallback`即可，

例如：这里是在请求参数里增加一个签名`sign`的字段。

回调中会回调给用户所有请求头和请求参数的列表数据.

范例：`SignHelper.kt`

```kotlin
object SignHelper {
    fun getSign(params:MutableMap<String, String>, header:MutableMap<String, String>){
        params.put("sign", "paramsign")
        header.put("sign", "headersign")
    }
}
}
```

# 请求定义

请求需要`OPHttp`对象，也就是总入口配置的对象，下面初始化时会生成这个对象。

范例：`MyRequest.kt`

```kotlin
object MyRequest {
    var opHttp:OPHttp? = null
    
    fun register(opHttp: OPHttp?) {
        this.opHttp = opHttp
    }
    /**
     * 取得用户信息
     * @param httpResponse
     */
    fun getUserInfo(
        uid: String,
        fields:String, httpResponse: OPResponse<Map<String, UserInfo>>
    ) {
        Rest.ophttp(opHttp).getRestApi(MyApi::class.java).getUserInfo(uid,fields)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(httpResponse)
    }
}
```

# 初始化

建议在自定义`Application`类中初始化

范例：`AppAplication.kt`

```kotlin
class AppAplication:Application() {
    override fun onCreate() {
        super.onCreate()
        initHttp()
    }


    /**
     * 初始化http
     */
    fun initHttp() {
        var requestPreCallback = object:IRequestPreCallback {
            override fun onRequestPre(params: MutableMap<String, String>, headers: MutableMap<String, String>) {
                SignHelper.getSign(headers, params)
            }
        }
        var ophttp = OPHttp.Builder().setHeaders(HttpConfig()).setSignCallback(requestPreCallback).domain(MyApi.DOMAIN).build()
        MyRequest.register(ophttp)
    }
}
```

# 请求

范例：`MainActivity.kt`

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqUserInfo()
    }
    private fun reqUserInfo() {
        MyRequest.getUserInfo("898210", "all", object:OPResponse<Map<String, UserInfo>>() {
            override fun onSuccess(t: Map<String, UserInfo>?) {
                LogUtils.d(t?.keys.toString() + "===")
            }

            override fun onFailed(code: Int, msg: String?) {
                super.onFailed(code, msg)
            }
        })
    }
}
```

# Release Version Note

## 1.0.0

first version issue

## 1.0.1

2020-08-05 update

## 1.0.2

2020-08-22 update

Change `Observable` to `Single` request.


