package com.jiutong.base.utils

import android.content.Context
import android.text.TextPaint

/**
 *
 *
 * 尺寸显示相关工具类
 */

object DisplayUtil {
    private var density = UIUtil.resources.displayMetrics.density
    private var scaledDensity = UIUtil.resources.displayMetrics.scaledDensity
    /**
     * dp 转换成 px
     *
     * @param dpValue dp值
     * @return
     */
    fun dp2px(dpValue: Int): Int {
        return (dpValue * density + 0.5f).toInt()
    }

    /**
     * px 转换成 dp
     *
     * @param pxValue px值
     * @return
     */
    fun px2dp(pxValue: Int): Int {
        return (pxValue / density + 0.5f).toInt()
    }

    /**
     * sp 转换成 px
     *
     * @param spValue sp值
     * @return
     */
    fun sp2px(spValue: Int): Int {
        return (spValue * scaledDensity + 0.5f).toInt()
    }

    /**
     * sp 转换成 px
     *
     * @param spValue sp值
     * @return
     */
    fun sp2pxF(spValue: Int): Float {
        return (spValue * scaledDensity + 0.5f)
    }

    /**
     * sp 转换成 px
     *
     * @param pxValue px值
     * @return
     */
    fun px2sp(pxValue: Int): Int {
        return (pxValue / scaledDensity + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    val screenWidth: Int
        get() = UIUtil.resources.displayMetrics.widthPixels

    /**
     * 获取状态栏高度
     *
     * @return 状态栏的高度(单位：px)
     */
    val statusBarHeight: Float
        get() {
            var result = 0f
            val resourceId = UIUtil.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = UIUtil.resources.getDimension(resourceId)
            }
            return result
        }

    /**
     * 获取导航栏高度
     *
     * @return 导航栏的高度(单位：px)
     */
    val navigationBarHeight: Float
        get() {
            var result = 0f
            val resourceId = UIUtil.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = UIUtil.resources.getDimension(resourceId)
            }
            return result
        }
    // 字体的宽度
    fun getTextIntWidth(Context: Context, text: String, textSize: Int): Int {
        val paint = TextPaint()
        val scaledDensity = Context.resources.displayMetrics.scaledDensity
        paint.textSize = scaledDensity * textSize
        return paint.measureText(text).toInt()
    }
}
