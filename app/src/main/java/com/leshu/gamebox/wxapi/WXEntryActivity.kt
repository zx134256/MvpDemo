package com.leshu.gamebox.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jiutong.base.utils.LogUtils
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.leshu.gamebox.utils.WxAndAliUtil

/**
 * 程序需要接收微信发送的请求，或者接收发送到微信请求的响应结果
 *
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    private var api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WxAndAliUtil.isWXAppInstalled(this)
        api?.handleIntent(intent,this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api?.handleIntent(intent,this)
    }

    /**
     *，发送到微信请求的响应结果将回调
     */
    override fun onResp(p0: BaseResp?) {
        p0.let { LogUtils.d("zx", it?.errStr +"") }
        finish()

    }

    /**
     * 微信发送的请求将回调
     */
    override fun onReq(p0: BaseReq?) {

    }
}