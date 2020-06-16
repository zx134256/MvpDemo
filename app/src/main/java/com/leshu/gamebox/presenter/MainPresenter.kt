package com.leshu.gamebox.presenter

import ApiClient
import android.content.Context
import com.jiutong.base.utils.DeviceUtil
import com.jiutong.base.utils.LogUtils
import com.jiutong.haofahuo.util.UserFileUtil
import com.leshu.gamebox.MainActivity
import com.leshu.gamebox.base.baseEntity.BaseResult
import com.leshu.gamebox.base.componet.BasePresenter
import com.leshu.gamebox.base.http.BaseObserver
import com.leshu.gamebox.base.http.RxTransformer
import com.leshu.gamebox.contract.MainContract
import com.leshu.gamebox.entity.UserDeviceEntity
import com.trello.rxlifecycle2.android.ActivityEvent

class MainPresenter : BasePresenter<MainActivity>(), MainContract.IMainPresenter,
    MainContract.IDeviceLogin {

    override fun deviceLogin(context: Context) {
        ApiClient.mApiServer.deviceLogin(DeviceUtil.getMachineCode(context))
            .compose(RxTransformer.io2Main())
            .compose(getView()!!.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(object : BaseObserver<UserDeviceEntity>() {
                override fun onHttpSuccess(t: UserDeviceEntity) {
                    LogUtils.d("zx", t.toJson())
                    UserFileUtil.saveToken(context,t.accessToken?:"")
                    UserFileUtil.saveUid(context,t.uid)
                }
            })
    }

    override fun getGameList() {
        LogUtils.d("zx", "getGameList")
        val loginMap = HashMap<String, String>()
        loginMap["appId"] = "002"
        loginMap["loginType"] = "PHONE_PASSWORD"
        loginMap["username"] = "18888888884"
        loginMap["password"] = "123456"
        loginMap["tenantId"] = "2"
        ApiClient.mApiServer.getGameList()
            .compose(RxTransformer.io2Main())
            .compose(getView()!!.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(object : BaseObserver<BaseResult>() {
                override fun onHttpSuccess(t: BaseResult) {
                    LogUtils.d("zx", "success")
                    // getView()?.onGetGameListSuccess(t)
                }

            })


    }
}