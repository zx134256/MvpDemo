package com.leshu.gamebox.base.componet

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BasePresenter<out V : IBaseView> {
    val mCompositeDisposable by lazy { CompositeDisposable() }
    private var mWeakReference : WeakReference<V>? = null

    /**
     * 绑定View
     *
     * @param view
     */
    fun attachView(view: IBaseView) {
        mWeakReference = WeakReference(view as V)
    }

    /**
     * 解绑View
     */
    fun detachView() {
        if (mWeakReference != null) {
            mWeakReference?.clear()
            mWeakReference = null
        }
        mCompositeDisposable.clear()
    }

    /**
     * 获取View
     *
     * onDestroy() 调用后改该方法返回null
     * @return
     */
    fun getView(): V? {
        if (mWeakReference != null) {
            return mWeakReference?.get()
        }
        return null
    }
}