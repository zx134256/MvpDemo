package com.leshu.gamebox.entity

import com.leshu.gamebox.base.baseEntity.BaseEntity

class AliMsgEntity(val status: String) : BaseEntity() {
    companion object{
        val sophixRestart = "SOPHIX_RESTART"
        val sophixLoading = "SOPHIX_LOADING"
        val sophixNoUpdate = "SOPHIX_NO_UPDATE"
    }
}