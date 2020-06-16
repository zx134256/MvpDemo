package com.leshu.gamebox.base.componet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leshu.gamebox.base.MyApplication
import com.leshu.gamebox.base.constant.Constant
import com.trello.rxlifecycle2.components.RxFragment
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract  class BaseFragment : RxFragment(),
    IBaseView {

    protected lateinit var contentView : View
    protected  var title : String? = null
    val mCompositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.get(Constant.TITLE) as String?
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(resLayoutId, container, false)!!
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }


    /**
     * 资源id
     */
    protected abstract val resLayoutId : Int


    protected abstract fun initView(contentView : View)

    /**
     *
     * org.greenrobot.eventbus.EventBusException: Subscriber class com.jiutong.haofahuo.fragment.WaitDueFragment
     * and its super classes have no public methods with the @Subscribe annotation
     *
     * 如果要注册EventBus当前类或者父类最少要有一个 @Subscribe 注解方法
     */
    @Subscribe
    fun onSubscribe(str: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        //LeakCanary监控Fragment的内存泄漏 //LeakCanary监控Fragment的内存泄漏
        MyApplication.getRefWatcher().watch(this)
        EventBus.getDefault().unregister(this)
        mCompositeDisposable.clear()
    }




    override fun showLoading(text: String, cancelable: Boolean) {
        TODO("Not yet implemented")
    }

    override fun dismissLoading() {
        TODO("Not yet implemented")
    }
}