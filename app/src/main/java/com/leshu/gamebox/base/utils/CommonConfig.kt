package com.jiutong.base.utils


import androidx.databinding.BaseObservable
import com.google.gson.Gson

open class CommonConfig : BaseObservable() {

    companion object {

        fun <T> fromJson(json: String, clazz: Class<T>): T {
            return Gson().fromJson(json, clazz)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}