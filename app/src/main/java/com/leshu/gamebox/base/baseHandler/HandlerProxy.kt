package com.leshu.gamebox.base.baseHandler

import android.os.Handler
import android.os.Message
import java.lang.Exception

class HandlerProxy(private val mHandler: Handler) : Handler() {

    override fun handleMessage(msg: Message) {
        try {
            mHandler.handleMessage(msg)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }
}