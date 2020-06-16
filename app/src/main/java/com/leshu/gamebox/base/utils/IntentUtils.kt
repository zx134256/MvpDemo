package com.jiutong.base.utils

import android.app.Activity

object IntentUtils {
    fun intGetString(stringCode: String, context: Activity): String {
        var stringTemp = ""
        if (context.intent.getStringExtra(stringCode) != null) {
            stringTemp = context.intent.getStringExtra(stringCode)
        }
        return stringTemp
    }

    fun intGetInt(stringCode: String, defaultValue: Int, context: Activity): Int {
        var stringTemp = defaultValue
        if (context.intent.getIntExtra(stringCode, defaultValue) != null) {
            stringTemp = context.intent.getIntExtra(stringCode, defaultValue)
        }
        return stringTemp
    }

    fun <T> intGetData(stringCode: String, clazz: Class<T>, context: Activity): T? {
        var stringTemp = ""
        if (context.intent.getStringExtra(stringCode) != null) {
            stringTemp = context.intent.getStringExtra(stringCode)
            return CommonConfig.fromJson(stringTemp, clazz)
        }
        return null
    }


}