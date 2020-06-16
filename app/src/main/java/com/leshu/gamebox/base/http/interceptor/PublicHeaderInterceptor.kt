package com.leshu.gamebox.base.http.interceptor

import com.jiutong.base.utils.HttpRequestUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加公共header
 */
class PublicHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        HttpRequestUtil.publicHeader.forEach { builder.addHeader(it.key, it.value) }
        return chain.proceed(builder.build())
    }
}