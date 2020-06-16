package com.leshu.gamebox.base.http

import android.content.Context
import com.jiutong.base.utils.*
import com.leshu.gamebox.BuildConfig
import com.leshu.gamebox.R
import com.leshu.gamebox.base.baseEntity.BaseResult
import com.leshu.gamebox.base.componet.IBaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException


/**
 * BaseObserver
 *
 * 接口开始请求时调用onHttpStart方法，
 * 请求成功（code正确）调用onHttpSuccess方法，
 * 请求失败（HttpException或者code 错误）时调用onHttpError方法（当code返回失败时不解析data,直接抛出HttpErrorException异常，详见 {@link com.jiutong.base.http.GsonResponseBodyConverter}）
 * 请求结束调用onHttpEnd 方法
 *
 *
 *
 * @date: 2018/8/20 14:29
 */
abstract class BaseObserver<T : BaseResult>(private val iBaseView: IBaseView? = null) : DisposableObserver<T>() {
    private var showLoad: Boolean = true
    private var showMyLoadMsg: String = "正在加载..."
    private var cancelable: Boolean = false
    private var type: Int = 1

    /**
     * 默认 展示等待框  可实现取消 可实现重写展示msg
     */
    constructor(iBaseView: IBaseView? = null, showLoad: Boolean = false, cancelable: Boolean = false, showMyLoadMsg: String = "正在加载...", compositeDisposable: CompositeDisposable) : this(iBaseView) {
        this.showLoad = showLoad
        this.showMyLoadMsg = showMyLoadMsg
        this.cancelable = cancelable
        compositeDisposable.add(this)
        type = 3
    }

    /**
     * 默认 展示等待框  可实现取消
     */
    constructor(iBaseView: IBaseView? = null, showLoad: Boolean = false, cancelable: Boolean = false, compositeDisposable: CompositeDisposable) : this(iBaseView) {
        this.showLoad = showLoad
        this.cancelable = cancelable
        compositeDisposable.add(this)
        type = 2
    }

    /**
     * 默认 展示等待框 不可取消
     */
    constructor(iBaseView: IBaseView? = null, showLoad: Boolean = false, compositeDisposable: CompositeDisposable) : this(iBaseView) {
        this.showLoad = showLoad
        compositeDisposable.add(this)
        type = 1
    }

    /**
     * 请求开始
     *
     * 发起请求的时候调用，该方法一定会调用
     */
    open fun onHttpStart() {
        showLoad?.apply {
            if (showLoad) {
                when (type) {
                    1 -> iBaseView?.showLoading("正在加载...")
                    2 -> iBaseView?.showLoading("正在加载...", cancelable)
                    3 -> iBaseView?.showLoading(showMyLoadMsg, cancelable)
                }
            }
        }

    }

    /**
     * 请求成功
     *
     * http请求成功，并且服务端code返回正确的时候调用
     */
    abstract fun onHttpSuccess(t: T)

    /**
     * http失败或者code 错误的时候调用
     *
     * 默认打印Toast 错误信息
     */
    open fun onHttpError(e: HttpMyErrorException) {
        if (showLoad) iBaseView?.dismissLoading()
    }

    /**
     * 请求结束
     *
     * 请求结束的时候调用，无论请求结果如何，该方法一定会调用
     */
    open fun onHttpEnd() {
        if (showLoad) iBaseView?.dismissLoading()
    }

    /**
     * 订阅
     */
    override fun onStart() {
        super.onStart()

        try {
            onHttpStart()
        } catch (e: Exception) {
            showCodeException(e)
        }
    }

    /**
     * 成功
     */
    override fun onNext(t: T) {
        if (t.isSuccess()) {
            try {
                onHttpSuccess(t)
            } catch (e: Exception) {
                showCodeException(e)
            }
        }
        try {
            onHttpEnd()
        } catch (e: Exception) {
            showCodeException(e)
        }
    }

    /**
     * 请求失败
     * HttpException 时处理HttpException异常
     * HttpErrorException 时处理code 错误
     * 其他exception 不处理
     */
    override fun onError(e: Throwable) {
        try {
            //处理Http错误，如果是Http错误我们把Throwable向下转型成HttpException就可以获取Response信息
            if (e is HttpException) {
                try {
                    handleHttpExceptionCode(e)
                } catch (e: Exception) {
                    LogUtils.d(e.toString())
                }

                //处理后台返回的异常
            } else if (e is HttpMyErrorException) {
                handleExceptionCode(e)
                onHttpError(e)


                //处理代码exception
            } else {
                showCodeException(e)
                onHttpError(HttpMyErrorException(HttpMyErrorException.ERROR_SYSTEM, if (e.message != null) e.message!! else e.toString()))
            }
        } catch (e: Exception) {
            showCodeException(e)
        }

        try {
            onHttpEnd()
        } catch (e: Exception) {
            showCodeException(e)
        }
    }

    /**
     * 结束
     */
    override fun onComplete() {
        try {
            onHttpEnd()
        } catch (e: Exception) {
            showCodeException(e)
        }
    }

    /**
     * 单独处理自定义异常
     */
    private fun handleExceptionCode(exceptionMy: HttpMyErrorException) {
        LogUtils.d(exceptionMy.toString())
        if (exceptionMy != null) {
            if (exitCodeHandle(exceptionMy.code)) {

            } else if (exceptionMy.code == 401 || exceptionMy.code == -1) {
                ToastUtil.toast(exceptionMy.message)
            } else {
                ToastUtil.toast(exceptionMy.message)
            }
            onHttpError(HttpMyErrorException(exceptionMy.code, exceptionMy.message))
        } else {
            ToastUtil.toast(UIUtil.getString(R.string.system_error))
            onHttpError(HttpMyErrorException(HttpMyErrorException.ERROR_SYSTEM, UIUtil.getString(R.string.system_error)))
        }
    }

    /**
     * 单独处理http异常（非 200） 错误
     */
    private fun handleHttpExceptionCode(exception: HttpException) {
        LogUtils.d("HttpException" + exception.code().toString())
        when (exception.code()) {
            500, 501, 502, 503, 504 -> {
                ToastUtil.toast("服务器异常，稍后重试")
            }
            401 -> {
                ToastUtil.toast("无效的token,请重新登录")
            }
            404 -> {
                ToastUtil.toast("接口地址错误或者无效")
            }

        }

    }

    /**
     * 退出code处理
     */
    private fun exitCodeHandle(code: Int): Boolean {
        when (code) {

        }
        return false
    }

    /**
     * 显示代码导致的程序异常信息
     */
    private fun showCodeException(e: Throwable) {
        e.printStackTrace()
        if (BuildConfig.DEBUG) {
            try {
                iBaseView?.apply {
                    DialogUtil.showTipsDialog(iBaseView as Context, text = if (e.message != null) e.message!! else e.toString())
                }
            } catch (e: Exception) {
            }
        } else {
            ToastUtil.toast(R.string.system_error)


            //代码异常上传日志
            val timestamp = System.currentTimeMillis()
            /*val entity = JLogEntity(content = OtherUtil.getErrorInfoFromException(e), ip = "0.0.0.0", lever = 0, local = "what")
          ApiClient.mApiServer.uploadLog(timestamp, HttpRequestUtil.generateSign(timestamp, entity),
                   HttpRequestUtil.convertRequestBody(entity))
                   .compose(RxTransformer.io2Main())
                   .subscribe({}, {})*/
        }
    }
}