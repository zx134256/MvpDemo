package com.leshu.gamebox.base.baseEntity

import com.google.gson.annotations.Expose

open class BaseResult :BaseEntity() {
    companion object {
        fun isSuccess(code: Int): Boolean {
            return code == 0
        }
    }


    @Expose
    var code: Int = -1

    @Expose
    var msg = ""

    /**
     * code : 0  成功，其余失败
     */
    fun isSuccess(): Boolean {
        return code == 0
    }
}