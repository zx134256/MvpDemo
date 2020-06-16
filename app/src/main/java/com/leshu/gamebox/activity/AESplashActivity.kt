package com.leshu.gamebox.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.jiutong.base.utils.FileUtil
import com.leshu.gamebox.MainActivity
import com.leshu.gamebox.R
import com.leshu.gamebox.base.componet.BaseMVPActivity
import com.leshu.gamebox.base.http.RxTransformer
import com.leshu.gamebox.contract.WelComeContract
import com.leshu.gamebox.entity.AliMsgEntity
import com.leshu.gamebox.entity.AliUpdateEntity
import com.leshu.gamebox.presenter.WelComePresenter
import com.taobao.sophix.SophixManager
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

class AESplashActivity() : BaseMVPActivity<WelComeContract.IWelcomeView, WelComePresenter>(),
    WelComeContract.IWelcomeView {

    override fun onRequestAliUpdateInfoSuccess(aliUpdateEntity: AliUpdateEntity) {
        if (aliUpdateEntity.needUpdate()) {
        } else {
            EventBus.getDefault().post(AliMsgEntity(AliMsgEntity.sophixNoUpdate))
        }
    }

    override val resLayoutId: Int
        get() = R.layout.activity_splash

    override fun createView() = this

    override fun createPresenter() = WelComePresenter()

    var mIsStop = true
    var mIsPermissionsSuccess = false
    var mIsInitSuccess = false;
    private val mHandler by lazy { MyHandler(this) }
    private var mEmitter: ObservableEmitter<Int>? = null

    override fun showLoading(text: String, cancelable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun dismissLoading() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.create<Int> { e ->
            mEmitter = e
            init()
        }.compose(RxTransformer.io2Main())
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe {
                //mPresenter.requestAliUpdateInfo()
                EventBus.getDefault().post(AliMsgEntity(AliMsgEntity.sophixNoUpdate))
            }
    }

    private fun goToMainPage() {
        MainActivity.actionStartActivity(AESplashActivity@ this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAliUpdate(aliMsgEntity: AliMsgEntity) {
        when (aliMsgEntity.status) {
            AliMsgEntity.sophixRestart -> restartApp()
            AliMsgEntity.sophixNoUpdate -> goToMainPage()
        }
    }

    /**
     * 重启app
     */
    private fun restartApp() {
        val LaunchIntent = packageManager.getLaunchIntentForPackage(application.packageName)
        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(LaunchIntent)
        SophixManager.getInstance().killProcessSafely()
    }

    /**
     * 初始化操作(子线程)
     */
    private fun init() {
        FileUtil.clearPicCacheDir()
        mIsInitSuccess = true
        onNext()
    }

    /**
     * 初始化和获取权限成功，发射onNext
     */
    private fun onNext() {
        if (mIsInitSuccess && mIsPermissionsSuccess) {
            mEmitter?.onNext(1)
        }
    }


    override fun onResume() {
        super.onResume()
        if (mIsStop) {
            if (mHandler.activityWeakReference.get() == null) return
            mHandler.postDelayed({
                if (mPermissionsHelper.isDismiss) {
                    mPermissionsHelper.request(
                        {
                            mIsPermissionsSuccess = true
                            onNext()
                        }, true, false,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    mIsStop = false
                }
            }, 1000)
        }
    }

    override fun onStop() {
        super.onStop()
        mIsStop = true
    }

    companion object {
        private class MyHandler(activity: AESplashActivity) : Handler() {
            val activityWeakReference: WeakReference<AESplashActivity> = WeakReference(activity)
        }
    }

}