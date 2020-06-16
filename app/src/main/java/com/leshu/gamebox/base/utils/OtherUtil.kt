package com.jiutong.base.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import java.io.PrintWriter
import java.io.StringWriter



object OtherUtil {

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    fun call(context: Context, phone: String) {
        if (!phone.isNullOrBlank()) {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (e: Exception) {
                ToastUtil.toast("请使用真机进行打电话行为")
            } finally {

            }
        }
    }

    /**
     * 跳转GPS设置界面
     */
    fun openGPSSetting(context: Context) {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    /**
     * 将异常信息转换为String
     */
    fun getErrorInfoFromException(e: Throwable): String {
        try {
            val writer = StringWriter()
            val print = PrintWriter(writer)
            e.printStackTrace(print)
            return "\r\n" + writer.toString() + "\r\n"
        } catch (exception: Exception) {
            return "Exception转换为String失败 ${exception.message}"
        }
    }
}