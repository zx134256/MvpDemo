package com.leshu.gamebox.base.http.interceptor


import com.leshu.gamebox.BuildConfig
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * 公共参数拦截器
 */
class PublicParamsInterceptor : Interceptor {

    /**
     * 需要添加的公共参数
     *
     * @return
     */
    private val publicParams: Map<String, String>
        get() {
            val params = HashMap<String, String>()
            params.put("token", "")
            params.put("version", BuildConfig.VERSION_NAME)
            return params
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val publicParams = publicParams
        when (request.method()) {
            "GET" -> {
                //GET请求添加公共参数
                val httpBuilder = request.url().newBuilder()
                for ((key, value) in publicParams) {
                    httpBuilder.addQueryParameter(key, value)
                }
                val modifiedUrl = httpBuilder.build()
                request = request.newBuilder().url(modifiedUrl).build()
            }
            "POST" -> {
                val body = request.body()
                //POST请求FormBody添加公共参数
                if (body != null && body is FormBody) {
                    val formBody = body as FormBody?
                    val formParams = HashMap<String, String>()
                    for (i in 0 until formBody!!.size()) {
                        formParams.put(formBody.name(i), formBody.value(i))
                    }
                    formParams.putAll(publicParams)
                    val formBuilder = FormBody.Builder()
                    for ((key, value) in formParams) {
                        formBuilder.add(key, value)
                    }
                    request = request.newBuilder().method(request.method(), formBuilder.build()).build()

                    //POST请求MultipartBody添加公共参数
                } else if (body != null && body is MultipartBody) {
                    val multipartBody = body as MultipartBody?
                    val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
                    for ((key, value) in publicParams) {
                        multipartBuilder.addFormDataPart(key, value)
                    }
                    val parts = multipartBody!!.parts()
                    for (part in parts) {
                        multipartBuilder.addPart(part)
                    }
                    request = request.newBuilder().post(multipartBuilder.build()).build()
                }
            }
        }
        return chain.proceed(request)
    }
}
