package com.jiutong.base.componet.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import com.balysv.materialmenu.MaterialMenuDrawable

import com.jiutong.base.utils.ToastUtil
import com.jiutong.base.utils.UIUtil
import com.just.agentweb.*
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.R


import com.leshu.gamebox.base.componet.BaseActivity
import kotlinx.android.synthetic.main.activity_base_web.*
import kotlinx.android.synthetic.main.include_title_bar.*


/**
 * WebViewActivity
 */
open class BaseWebViewActivity : BaseActivity() {
    private lateinit var mUrl: String
    private var mDefaultTitle: String? = null
    private lateinit var mAgentWeb: AgentWeb

    companion object {
        const val URL = "url"
        const val DEFAULT_TITLE = "default_title"

        fun actionStartActivity(context: Context, url: String, defaultTitle: String?) {
            val intent = Intent(context, BaseWebViewActivity::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(DEFAULT_TITLE, defaultTitle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = intent.getStringExtra(URL)
        mDefaultTitle = intent.getStringExtra(DEFAULT_TITLE)

        if (mUrl.isBlank()) {
            ToastUtil.toast(R.string.web_url_error)
            finish()
            return
        }

        if (mDefaultTitle.isNullOrBlank()) {
            mDefaultTitle = UIUtil.getString(R.string.app_name)
        }


        initView()
        initWebView()
    }

    private fun initView() {
        initTitle(mDefaultTitle!!, iconState = MaterialMenuDrawable.IconState.X)

    }

    private fun initWebView() {
        mAgentWeb = AgentWeb.with(this)
                //设置父布局，不支持 ConstraintLayout
                .setAgentWebParent(mLlWebViewContainer, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .useDefaultIndicator(-1, 2)
                //设置 IAgentWebSettings
                .setAgentWebWebSettings(getSettings())
                //设置WebViewClient ，与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效
//                .setWebViewClient(mWebViewClient)
                //设置WebChromeClient
                .setWebChromeClient(mWebChromeClient)
                //权限拦截
                .setPermissionInterceptor(mPermissionInterceptor)
                //安全模式，STRICT_CHECK -> 严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响
                .setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK)
                //自定义UI
//                .setAgentWebUIController(UIController(this))
                //错误页面，参数1是错误显示的布局，参数2是  点击刷新控件ID   -1表示点击整个布局都刷新
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                //设置WebChromeClient中间件，支持多个WebChromeClient
//                .useMiddlewareWebChrome(getMiddlewareWebChrome())
                //设置WebViewClient中间件,支持多个WebViewClient
//                .useMiddlewareWebClient(getMiddlewareWebClient())
                //打开其他页面时是否弹窗询问
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                //拦截找不到相关页面的Url
                .interceptUnkownUrl()
                //创建AgentWeb
                .createAgentWeb()
                //设置 WebSettings
                .ready()
                //WebView载入该url地址的页面并显示
                .go(mUrl)

        //开启调试模式
        if (BuildConfig.DEBUG) {
            AgentWebConfig.debug()
        }
    }

    //处理返回键
    override fun onBackPressed() {
        if (!mAgentWeb.back()) {
            super.onBackPressed()
        }
    }

    override fun showLoading(text: String, cancelable: Boolean) {

    }

    override fun dismissLoading() {

    }


    private fun getSettings(): IAgentWebSettings<WebSettings> {
        return AgentWebSettingsImpl.getInstance()
    }


    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {

        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            mTvTitle?.text = title
        }
    }

    private var mPermissionInterceptor: PermissionInterceptor = PermissionInterceptor { url, permissions, action ->
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        false
    }

    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb.webLifeCycle.onDestroy()
    }

    override val resLayoutId: Int
        get() = R.layout.activity_base_web

}