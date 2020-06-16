package com.leshu.gamebox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.jiutong.base.utils.LogUtils
import com.jiutong.base.utils.RxViewUtils
import com.leshu.gamebox.base.componet.BaseMVPActivity
import com.leshu.gamebox.contract.MainContract
import com.leshu.gamebox.entity.GameListEntity
import com.leshu.gamebox.entity.UserDeviceEntity
import com.leshu.gamebox.presenter.MainPresenter
import com.leshu.gamebox.utils.WxAndAliUtil
import com.taobao.sophix.SophixManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMVPActivity<MainContract.IMainView, MainPresenter>(),
    MainContract.IMainView {


    override fun onDeviceLoginSuccess(userDeviceEntity: UserDeviceEntity) {
        getGameList?.let {
            RxViewUtils.clicks(getGameList, this).subscribe { mPresenter.getGameList() }
        }
    }

    override fun showLoading(text: String, cancelable: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetGameListSuccess(gameListEntity: GameListEntity) {
        LogUtils.d("zx", gameListEntity.toString() + "")
    }


    override fun createView(): MainContract.IMainView {
        return this
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override val resLayoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SophixManager.getInstance().queryAndLoadNewPatch();
        deviceLogin?.let {
            RxViewUtils.clicks(deviceLogin,this).subscribe {  mPresenter.deviceLogin(this) }
        }

        hello_world?.let {
            RxViewUtils.clicks(hello_world, this)
                .subscribe { WxAndAliUtil.wxLogin(MainActivity@ this) }
        }

    }



    companion object {
        fun actionStartActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
