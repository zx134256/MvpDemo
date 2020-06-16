package com.jiutong.base.utils

import android.app.ActivityManager
import android.content.Context

object ActivityUtil {
    /**
     * 获取栈顶activity
     * @return 完整类名
     */
    fun getTopActivity(context: Context): String? {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = activityManager.getRunningTasks(1)
        if (runningTasks != null && runningTasks.isNotEmpty()) {
            return runningTasks[0].topActivity?.className
        }
        return null
    }
}