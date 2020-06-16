package com.jiutong.haofahuo.util

import android.app.Activity
import android.content.Context

object UserFileUtil {
    /**
     * 共用的文件
     */
    private val PUBLIC_FILE = "public_file"

    /**
     * 设备ID
     */
    private const val DEVICE_ID = "device_id"


    /**
     * 设备ID
     */
    private const val TOKEN = "token"

    /**
     * uid
     */
    private const val UID = "uid"

    /**
     * 读取device id
     */
    fun readDeviceId(context: Context): String {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        return prefs.getString(DEVICE_ID, "").toString()
    }

    /**
     * 保存device id
     */
    fun saveDeviceId(context: Context, deviceId: String) {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        prefs.edit().putString(DEVICE_ID, deviceId).commit()
    }


    /**
     * 读取token
     */
    fun readToken(context: Context): String {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        return prefs.getString(DEVICE_ID, "").toString()
    }

    /**
     * 保存token
     */
    fun saveToken(context: Context, deviceId: String) {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        prefs.edit().putString(DEVICE_ID, deviceId).commit()
    }

    /**
     * 读取device id
     */
    fun readUid(context: Context): String {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        return prefs.getString(DEVICE_ID, "").toString()
    }

    /**
     * 保存device id
     */
    fun saveUid(context: Context, deviceId: Int) {
        val prefs = context.getSharedPreferences(PUBLIC_FILE, Activity.MODE_PRIVATE)
        prefs.edit().putInt(DEVICE_ID, deviceId).commit()
    }

}