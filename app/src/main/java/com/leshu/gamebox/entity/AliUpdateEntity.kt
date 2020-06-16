package com.leshu.gamebox.entity

import com.leshu.gamebox.base.baseEntity.BaseResult

class AliUpdateEntity : BaseResult() {
    var hotupdate: Int = 0
    var status : Int = 0
    var appurl : String? = ""
    var info : String? = ""
    var version : String? = ""

    fun needUpdate() = hotupdate == 1
}
