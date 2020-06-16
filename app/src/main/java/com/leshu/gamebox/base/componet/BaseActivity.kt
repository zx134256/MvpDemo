package com.leshu.gamebox.base.componet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.balysv.materialmenu.MaterialMenuDrawable
import com.jiutong.base.helper.PermissionsHelper
import com.jiutong.base.utils.ActivityControllerUtil
import com.jiutong.base.utils.DisplayUtil
import com.jiutong.base.utils.RxViewUtils
import com.jiutong.base.utils.UIUtil
import com.leshu.gamebox.R
import com.leshu.gamebox.base.MyApplication
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.include_title_bar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : RxAppCompatActivity(), IBaseView, LifecycleOwner {
    private var mIsFullScreen = false
    private var dataBindingView: ViewDataBinding? = null
    private lateinit var mLifecycleRegistry: LifecycleRegistry
    val mPermissionsHelper by lazy { PermissionsHelper(this) }
    private var mIsSwipeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingView =
            DataBindingUtil.inflate(LayoutInflater.from(this), resLayoutId, null, false)

        if (dataBindingView == null) {
            setContentView(resLayoutId)
        } else {
            setContentView(dataBindingView?.root)
            dataBindingView?.lifecycleOwner = this
        }

        mLifecycleRegistry = LifecycleRegistry(this)

        mLifecycleRegistry.currentState = Lifecycle.State.CREATED
        mLifecycleRegistry.addObserver(object : LifecycleObserver {

        })

        ActivityControllerUtil.add(this)
        EventBus.getDefault().register(this)

        if (!mIsFullScreen) {
            /*todo fix 适配状态栏？*/

        }

        hideTitleBarRight()

    }

    /**
     *
     * org.greenrobot.eventbus.EventBusException: Subscriber class com.jiutong.haofahuo.fragment.WaitDueFragment
     * and its super classes have no public methods with the @Subscribe annotation
     *
     * 如果要注册EventBus当前类或者父类最少要有一个 @Subscribe 注解方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSubscribe(str: String) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        super.onStop()
    }

    fun getBindingView(): ViewDataBinding {
        return dataBindingView!!
    }

    private fun hideTitleBarRight() {

    }

    /**
     * Android6.0及以上状态栏导航栏适配，设置为白色背景，黑色字体
     */
    private fun setStatusAndNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //取消设置透明状态栏及导航栏,使 ContentView 内容不再覆盖状态栏及导航栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置状态栏颜色
            window.statusBarColor = UIUtil.getColor(R.color.white)
            //设置导航栏颜色
            window.navigationBarColor = UIUtil.getColor(R.color.white)
            //设置状态栏和导航栏文本颜色
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    /**
     * 设置activity全屏显示，必须在setContentView（）之前调用
     */
    protected fun setFullScreen() {
        mIsFullScreen = true
        // 隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 设置全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //隐藏导航栏
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        super.onPause()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        ActivityControllerUtil.remove(this)
        MyApplication.getRefWatcher().watch(this)
        dismissLoading()
        RxViewUtils.mCompositeDisposable.clear()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 初始化title
     */
    protected fun initTitle(
        resId: Int,
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        mTvTitle?.setText(resId)
        initTitle(isBack, iconState)
    }


    protected fun initTitle(
        title: String,
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        mTvTitle?.text = title
        initTitle(isBack, iconState)
    }

    private fun initTitle(
        isBack: Boolean = true,
        iconState: MaterialMenuDrawable.IconState = MaterialMenuDrawable.IconState.ARROW
    ) {
        setSupportActionBar(mToolbar)
        supportActionBar?.title = ""
        if (isBack) {
            val materialMenu = MaterialMenuDrawable(
                this,
                UIUtil.getColor(R.color.grey_979797),
                MaterialMenuDrawable.Stroke.THIN
            )
            materialMenu.iconState = iconState
            //显示左上角返回图标
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //自定义返回图标
            supportActionBar?.setHomeAsUpIndicator(materialMenu)
            //点击事件
            mToolbar.setNavigationOnClickListener {
                finish()
            }
            //有返回键则有滑动返回
            mIsSwipeEnabled = true
        } else {
            //隐藏左上角图标
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mIsSwipeEnabled = false
        }
    }


    /**
     * 设置右侧按钮
     */
    protected fun setTvRight(resId: Int, click: (() -> Unit)?) {
        hideTitleBarRight()
        mTvTitleRight?.setText(resId)
        mTvTitleRight?.visibility = View.VISIBLE
        RxViewUtils.clicks(mTvTitleRight, this)
            .subscribe { click?.invoke() }
    }

    /**
     * 设置右侧带图片的文字按钮
     */
    protected fun setTvIcRight(resId: Int, icRes: Int, click: (() -> Unit)?) {
        hideTitleBarRight()
        mLlTitleRight?.visibility = View.VISIBLE
        val drawable = resources.getDrawable(icRes)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        mTvIcRight.setCompoundDrawables(null, drawable, null, null)
        mTvIcRight.compoundDrawablePadding = DisplayUtil.dp2px(5)
        mTvIcRight.setText(resId)
        RxViewUtils.clicks(mTvIcRight, this)
            .subscribe { click?.invoke() }
    }

    protected abstract val resLayoutId: Int

    companion object {
        val TAG = this.javaClass.simpleName
    }
}