package com.leshu.gamebox.utils

import android.content.Context
import android.content.pm.PackageManager

object SettingInfoUtil {

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context?): String {
        var version = ""
        if (context == null)
            return ""
        try {
            // 获取packagemanager的实例
            val packageManager = context.packageManager
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            val packInfo = packageManager.getPackageInfo(context.packageName, 0)
            version = packInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    @Synchronized
    fun getPackageName(context: Context?): String {
        if (context == null) {
            return "0"
        }
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            return packageInfo.packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "0"
    }

    /**
     * 获取渠道名
     *
     * @param context 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    fun getChannelName(context: Context?): String? {
        if (context == null) {
            return null
        }
        var channelName: String? = null
        try {
            val packageManager = context.packageManager
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                val applicationInfo = packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL").toString()
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return channelName
    }

    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            return packageInfo.applicationInfo.loadLabel(packageManager).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}