package com.leshu.gamebox.presenter

import ApiClient
import com.jiutong.base.utils.LogUtils
import com.leshu.gamebox.activity.AESplashActivity
import com.leshu.gamebox.base.componet.BasePresenter
import com.leshu.gamebox.base.http.BaseObserver
import com.leshu.gamebox.base.http.RxTransformer
import com.leshu.gamebox.contract.WelComeContract
import com.leshu.gamebox.entity.AliUpdateEntity
import com.leshu.gamebox.utils.SettingInfoUtil
import com.trello.rxlifecycle2.android.ActivityEvent

class WelComePresenter : BasePresenter<AESplashActivity>(), WelComeContract.IWelcomePresenter {

    override fun requestAliUpdateInfo() {
        val aliFieldMap = HashMap<String, String>()
        aliFieldMap["version"] = SettingInfoUtil.getVersionName(getView())
        aliFieldMap["packName"] = SettingInfoUtil.getPackageName(getView())
        aliFieldMap["channel"] = (SettingInfoUtil.getChannelName(getView()) ?: "").toString()
        aliFieldMap["appName"] = SettingInfoUtil.getAppName(getView()!!) ?: ""
        ApiClient.mApiServer.requestAliUpdate(aliFieldMap)
            .compose(RxTransformer.io2Main())
            .compose(getView()!!.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(object : BaseObserver<AliUpdateEntity>() {
                override fun onHttpSuccess(t: AliUpdateEntity) {
                    getView()?.onRequestAliUpdateInfoSuccess(t)
                    LogUtils.d("zx", t.hotupdate.toString() + "   ${t.hotupdate}")
                }

            })
    }
}