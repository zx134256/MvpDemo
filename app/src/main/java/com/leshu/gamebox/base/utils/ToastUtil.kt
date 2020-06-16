package com.jiutong.base.utils

import android.os.Handler
import android.view.Gravity
import android.widget.Toast
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.base.MyApplication
import com.leshu.gamebox.base.baseHandler.HandlerProxy

/**
 * Toast 工具类
 * 耗时操作前执行toast展示
 * 原因是7.1.1系统对TYPE_TOAST的Window类型做了超时限制，绑定了Window Token，最长超时时间是3.5s，如果UI在这段时间内没有执行完，
 * Toast.show()内部的handler message得不到执行，NotificationManageService那端会把这个Toast取消掉，同时把Toast对于的window token置为无效
 */


object ToastUtil {

    fun toast(resId: Int) {
        toast(null, resId, Toast.LENGTH_SHORT, false)
    }

    fun toast(text: String) {
        toast(text, -1, Toast.LENGTH_SHORT, false)
    }

    fun toastCenter(text: String) {
        toast(text, -1, Toast.LENGTH_SHORT, true)
    }

    fun toastCenter(resId: Int) {
        toast(null, resId, Toast.LENGTH_SHORT, true)
    }

    /**
     * debug模式下打印toast
     */
    fun toastDebug(resId: Int) {
        if (BuildConfig.DEBUG) {
            toast(null, resId, Toast.LENGTH_SHORT, true)
        }
    }

    /**
     * debug模式下打印toast
     */
    fun toastDebug(text: String) {
        if (BuildConfig.DEBUG) {
            toast(text, -1, Toast.LENGTH_SHORT, true)
        }
    }

    fun hookToast(toast: Toast) {
        val cToast = Toast::class.java
        try {
            //TN是private的
            val fTn = cToast.getDeclaredField("mTN")
            fTn.isAccessible = true

            //获取tn对象
            val oTn = fTn.get(toast)
            //获取TN的class，也可以直接通过Field.getType()获取。
            val cTn = oTn.javaClass
            val fHandle = cTn.getDeclaredField("mHandler")

            //重新set->mHandler
            fHandle.isAccessible = true
            fHandle.set(oTn, HandlerProxy(fHandle.get(oTn) as Handler))
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 线程安全的toast
     *
     * @param text
     * @param resId
     * @param duration
     * @param isCenter
     */
    private fun toast(text: String?, resId: Int, duration: Int, isCenter: Boolean) {
        val toast = Toast.makeText(MyApplication.getInstance().applicationContext, text
                ?: UIUtil.getString(resId), duration)
        if (isCenter) {
            toast.setGravity(Gravity.CENTER, 0, 0)
        }
       // hookToast(toast)
        if (UIUtil.isRunMainThread) {
            toast.show()
        } else {
            UIUtil.postMainThread({ toast.show() }, this)
        }
    }
}
