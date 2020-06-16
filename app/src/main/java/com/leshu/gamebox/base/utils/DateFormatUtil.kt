package com.jiutong.base.utils

import java.text.SimpleDateFormat

object DateFormatUtil {
    /**
     * 将时间格式化获取毫秒值
     *
     *  yyyy-MM-dd HH:mm:ss
     */
    fun getDateFormStr(dateStr: String): Long {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr)
            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatTime(date: Long): String {
        try {
            return SimpleDateFormat("HH:mm:ss").format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatAllTime(date: Long): String {
        try {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatNianTime(date: Long): String {
        try {
            return SimpleDateFormat("yyyy").format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatYueTime(date: Long): String {
        try {
            return SimpleDateFormat("MM").format(date) + "月"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatRiTime(date: Long): String {
        try {
            return SimpleDateFormat("dd").format(date) + "日"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatShiTime(date: Long): String {
        try {
            return SimpleDateFormat("HH").format(date) + "时"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     *  时间格式化
     *
     *   HH:mm:ss
     */
    fun formatFenTime(date: Long): String {
        try {
            return SimpleDateFormat("mm").format(date) + "分"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}