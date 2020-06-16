package com.leshu.gamebox.base

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.multidex.MultiDex
import com.jiutong.base.utils.UIUtil
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.R
import com.leshu.gamebox.base.baseHandler.HandlerProxy
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mMApplication = this
        mMainThreadId = Thread.currentThread().id
        mMainThreadHandler = HandlerProxy(Handler())

        /*init leakCannary*/
        mRefWatcher = LeakCanary.install(getInstance())

        initLogger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
       // MultiDex.install(base) 阿里热更新application install
    }



    /**
     * 设置Logger
     */
    private fun initLogger() {
        val setting = Logger.init(TAG)
        //非debug模式下不打印日志
        if (!BuildConfig.DEBUG) {
            setting.logLevel(LogLevel.NONE)
        }
    }

    companion object {

        /**
         * Logger日志全局Tag
         */
        private val TAG = "AudioExplore"
        private lateinit var mMApplication: Application
        private lateinit var mRefWatcher: RefWatcher
        private var mMainThreadId: Long = 0
        private lateinit var mMainThreadHandler: HandlerProxy

        /**
         * 获取Application Context
         */
        fun getInstance() = mMApplication

        /**
         * 获取LeakCanary用来监听内存泄漏的对象
         */
        fun getRefWatcher() = mRefWatcher

        /**
         * 获取主线程id
         */
        fun getMainThreadId() = mMainThreadId

        /**
         * 获取主线程handler
         */
        fun getMainThreadHandler() = mMainThreadHandler

        //静态代码块
        init {
            initSmartRefreshLayout()
        }

        /**
         * 设置SmartRefreshLayout  header footer样式
         * 此种设置方式优先级最低
         */
        private fun initSmartRefreshLayout() {
            //设置全局的SmartRefreshLayout Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                //MD样式
                MaterialHeader(context).setColorSchemeColors(UIUtil.getColor(R.color.colorAccent))
                //热气球
//                val header = DeliveryHeader(context)
//                header.setBackgroundColor(UIUtil.getColor(R.color.grey_D3D3D3))
//                header
                //飞机样式
//                TaurusHeader(context)
            }

            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                //脉冲样式
                BallPulseFooter(context).setAnimatingColor(UIUtil.getColor(R.color.colorAccent))
                    .setNormalColor(UIUtil.getColor(R.color.srl_btn))
                    .setIndicatorColor(UIUtil.getColor(R.color.srl_btn))
            }
        }
    }


}