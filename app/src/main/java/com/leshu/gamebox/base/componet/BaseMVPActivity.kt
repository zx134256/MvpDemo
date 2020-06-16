package com.leshu.gamebox.base.componet

import android.os.Bundle
import android.os.PersistableBundle


/**
 * MVP activity基础类
 *
 * @author: xiangyun_liu
 *
 * @date: 2018/8/6 11:18
 */
abstract class BaseMVPActivity<out V : IBaseView, out P : BasePresenter<*>> : BaseActivity() {
    private val mView: V by lazy { createView() }
    protected val mPresenter: P by lazy { createPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(mView)
    }

    /**
     * 创建View
     *
     * @return
     */
    protected abstract fun createView(): V

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}

