package com.jiutong.base.utils

import android.util.Base64
import java.nio.charset.Charset

object Base64Util {
    /**
     * 默认字符集 utf-8
     */
    private val charsetName = "utf-8"

    /**
     * 解码
     */
    fun decode(str: String, charset: Charset? = null): String {
        return String(Base64.decode(str, Base64.DEFAULT), charset ?: charset(charsetName))
    }
}