package com.jiutong.base.utils


/**
 *
 * @Description:  数据转换
 */
object StringUtils {
    fun getListByString(strings: String): MutableList<String>? {
        if (strings.isNullOrBlank())
            return null
        var listData = mutableListOf<String>()
        strings.split(",").forEach {
            listData.add(it)
        }
        return listData
    }

    fun getStringByList(listData: MutableList<String>): String {
        var stringData = ""
        listData.forEach {
            stringData += "$stringData,"
        }
        if (stringData.isNullOrBlank()) return "" else return stringData.substring(0, stringData.length - 1)
    }

    fun isEmpty(strings: String): Boolean {
        return strings.isNullOrBlank()
    }
}