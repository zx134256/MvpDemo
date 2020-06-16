package com.leshu.gamebox.utils

import android.content.Context
import com.jiutong.base.utils.ToastUtil
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.R
import com.leshu.gamebox.base.componet.BaseActivity
import com.leshu.gamebox.base.http.RxTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 *
 * 支付
 *
 */
object WxAndAliUtil {

    /**
     * 支付宝支付
     */
    /*fun aliPay(activity: BaseActivity, orderInfo: String, callBack: Consumer<Boolean>) {
        Observable.create<Map<String, String>> { emitter ->
            val result = PayTask(activity).payV2(orderInfo, true)
            emitter.onNext(result)
            //获取支付状态
        }.map { result -> result["resultStatus"] }
            .compose(RxTransformer.io2Main())
            .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe({ resultStatus ->
                when (resultStatus) {
                    //判断resultStatus 为9000则代表支付成功
                    "9000" -> {
                        ToastUtil.toast(R.string.success_pay)
                        callBack.accept(true)
                    }
                    else -> {
                        callBack.accept(false)
                        ToastUtil.toast(R.string.failure_pay)
                    }
                }
            },
                { e ->
                    e.printStackTrace()
                    ToastUtil.toast(R.string.failure_pay)
                    callBack.accept(false)
                })
    }*/

//    /**
//     * 微信支付
//     * 内部判断了是否安装了微信app，以及版本是否支持支付
//    fun wxPay(context: Context, orderInfo: WeChatPayEntity) {
//        val api = isWXAppInstalled(context)
//        when {
//            api != null -> when {
//                isWXSupportApi(api) -> {
//                    api.registerApp(BuildConfig.WX_APP_ID)
//                    val request = PayReq()
//                    request.appId = orderInfo.appid
//                    request.partnerId = orderInfo.partnerid
//                    request.prepayId = orderInfo.prepayid
//                    request.packageValue = orderInfo.packageSign
//                    request.nonceStr = orderInfo.noncestr
//                    request.timeStamp = orderInfo.timestamp
//                    request.sign = orderInfo.sign
//                    api.sendReq(request)
//                }
//                else -> ToastUtil.toast(R.string.error_wx_app)
//            }
//            else -> ToastUtil.toast(R.string.no_wx_app)
//        }
//    }*/

    fun wxLogin(context: Context) {
        val  api = isWXAppInstalled(context)
        if (api != null) {
            api.registerApp(BuildConfig.WX_APP_ID)
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test"
            api.sendReq(req)
        } else{
            ToastUtil.toast(R.string.error_wx_app)
        }


    }

    /**
     * 是否了安装微信
     * @return 没有安装微信返回null
     */
    fun isWXAppInstalled(context: Context): IWXAPI? {
        val api = WXAPIFactory.createWXAPI(context, BuildConfig.WX_APP_ID, false)
        return if (api.isWXAppInstalled) api else null
    }

    /**
     * 当前微信版本是否支持支付
     */
    fun isWXSupportApi(api: IWXAPI): Boolean {
        return api.wxAppSupportAPI >= Build.PAY_SUPPORTED_SDK_INT
    }
}