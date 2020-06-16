package com.leshu.gamebox.base.componet

import android.os.Bundle

abstract class BaseMVPFragment<out V : IBaseView,out P : BasePresenter<*>> : BaseFragment(){

    private val mView by lazy { createView() }
    private val mPresenter by lazy { createPresenter() }

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