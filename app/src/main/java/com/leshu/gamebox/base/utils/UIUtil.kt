package com.jiutong.base.utils

import android.content.res.Resources
import com.leshu.gamebox.base.MyApplication
import java.lang.ref.WeakReference

/**

 *
 * UI操作相关工具类
 */

object UIUtil {
    /**
     * 获取resources对象
     *
     * @return
     */
    val resources: Resources
        get() = MyApplication.getInstance().resources

    /**
     * 当前线程是否是主线程
     */
    val isRunMainThread: Boolean
        get() = MyApplication.getMainThreadId() === Thread.currentThread().id

    /**
     * 获取字符串资源
     *
     * @param resId
     * @return
     */
    fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    /**
     * 获取字符串资源
     *
     * @param resId
     * @return
     */
    fun getString(resId: Int, vararg formatArgs: Any): String {
        return resources.getString(resId, formatArgs)
    }

    /**
     * 获取颜色资源
     *
     * @param resId
     * @return
     */
    fun getColor(resId: Int): Int {
        return resources.getColor(resId)
    }


    /**
     * post runnable到主线程
     *
     * @param runnable
     */

    fun postMainThread(run: () -> Unit, activity: Any) {
        MyApplication.getMainThreadHandler().post(MyRunnable(activity).reference?.run { run })
    }

    /**
     * 延迟post runnable到主线程
     * @param runnable
     * @param delayMillis
     *
     */
    fun postMainThreadDelayed(run: () -> Unit, activity: Any, delayMillis: Long = 4000) {
        MyApplication.getMainThreadHandler().postDelayed(MyRunnable(activity).reference?.run { run }, delayMillis)

    }

    private class MyRunnable(activity: Any) : Runnable {
        internal var reference: WeakReference<Any> = WeakReference(activity)

        override fun run() {}
    }
}
