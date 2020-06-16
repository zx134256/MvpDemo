package com.jiutong.base.utils

import java.text.DecimalFormat

object AmountUtil {
    /**
     *  保留两位小数，三位数一分隔
     *  1,002,200,999.22
     */
    private val decimalFormat = DecimalFormat("######0.00")

    /**
     * 分转换成元
     *
     * @param amount 金额，单位：分
     * @return 金额，单位：元
     */
    fun convertYuan(amount: Long): Double {
        return MathUtil.div(amount.toDouble(), 100.0)
    }

    /**
     * 元转换成分
     *
     * @param amount 金额，单位：元
     * @return 金额，单位：分
     */
    fun convertFen(amount: Double): Long {
        return MathUtil.mul(amount, 100.0).toLong()
    }

    /**
     * 金额格式化
     * @param amount 金额，单位：元
     */
    fun amountFormat(amount: Double): String {
        return decimalFormat.format(amount)
    }

    /**
     * 金额格式化
     * @param amount 金额，单位：分
     */
    fun amountFormat(amount: Long): String {
        if (amount.toString().isNullOrBlank()) {
            return ""
        }
        return amountFormat(convertYuan(amount))
    }
}