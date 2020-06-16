package com.leshu.gamebox.entity

import com.google.gson.annotations.Expose
import com.leshu.gamebox.base.baseEntity.BaseEntity
import com.leshu.gamebox.base.baseEntity.BaseResult

class LoginResult :BaseResult(){
    @Expose
    val data = Data()

    class Data : BaseEntity() {
        /**
         * token
         */
        @Expose
        val token: String = ""
//        /**
//         * 用户名
//         */
//        @Expose
//        val username = ""
//        /**
//         *deptId 部门ID(122:揽货部，123=揽货司机，124=揽货员兼司机)
//         */
//        @Expose
//        val deptId = ""

        @Expose
        val role : Array<String>?= null
    }
}