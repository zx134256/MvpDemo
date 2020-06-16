package com.jiutong.base.utils

import android.app.Activity

/**
 *
 *
 * 程序中所有Activity的控制器
 */

object ActivityControllerUtil {
    private val activities = mutableListOf<Activity>()

    /**
     * 添加activity
     *
     *
     * onCreate 中调用该方法
     *
     * @param activity
     */
    fun add(activity: Activity?) {
        if (activity != null) {
            activities.add(activity)
        }
    }

    /**
     * 删除activity
     *
     *
     * onDestroy 中调用该方法
     *
     * @param activity
     */
    fun remove(activity: Activity?) {
        if (activity != null) {
            activities.remove(activity)
        }
    }

    /**
     * 清除并关闭所有的activity
     */
    fun clear() {
        activities
                .filterNot { it.isDestroyed }
                .forEach { it.finish() }
        activities.clear()
    }
}
