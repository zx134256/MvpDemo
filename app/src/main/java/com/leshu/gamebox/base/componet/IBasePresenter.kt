package com.leshu.gamebox.base.componet

interface IBasePresenter {

    val upTime: Long
        get() = System.currentTimeMillis()
}