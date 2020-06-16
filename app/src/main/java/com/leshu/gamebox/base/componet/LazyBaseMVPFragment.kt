package com.jiutong.base.componet

import android.os.Bundle
import android.view.View
import com.leshu.gamebox.base.componet.BaseMVPFragment
import com.leshu.gamebox.base.componet.BasePresenter
import com.leshu.gamebox.base.componet.IBaseView

/**
 * 懒加载Fragment
 */
abstract class LazyBaseMVPFragment<out V : IBaseView, out P : BasePresenter<*>> : BaseMVPFragment<V, P>() {
    /**
     * Fragment的View加载完毕的标记
     */
    private var mIsViewCreated: Boolean = false
    /**
     * Fragment对用户可见的标记
     */
    private var mIsUIVisible: Boolean = false

    /**
     *  是否初始化
     */
    protected var mIsInit: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewCreated = true
        lazyLoad()
    }

    /**
     * setUserVisibleHint(boolean isVisibleToUser)方法会多次回调,而且可能会在onCreateView()方法执行完毕之前回调.
     * 如果isVisibleToUser==true,然后进行数据加载和控件数据填充,但是onCreateView()方法并未执行完毕,
     * 此时就会出现NullPointerException空指针异常.
     * 基于以上原因,我们进行数据懒加载的时机需要满足两个条件
     * onCreateView()方法执行完毕
     * setUserVisibleHint(boolean isVisibleToUser)方法返回true
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsUIVisible = isVisibleToUser
        lazyLoad()
    }

    private fun lazyLoad() {
        if (mIsViewCreated && mIsUIVisible) {
            lazyInitView(contentView)
            //数据加载完毕,恢复标记,防止重复加载
            mIsViewCreated = false
            mIsUIVisible = false
            mIsInit = true
        }
    }

    /**
     * 初始化
     *
     * @param contentView
     */
    abstract fun lazyInitView(contentView: View)

    /**
     * 该方法是在正常Fragment加载时初始化View,懒加载Fragment使用lazyInitView方法初始化
     */
    override fun initView(contentView: View) {
    }
}