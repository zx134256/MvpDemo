package com.jiutong.base.utils

import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.leshu.gamebox.base.componet.BaseActivity
import com.leshu.gamebox.base.componet.BaseFragment
import com.leshu.gamebox.base.http.RxTransformer

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


object RxViewUtils {

    val mCompositeDisposable by lazy { CompositeDisposable() }
    //view点击事件
    fun clicks(view: View, anyActivity: Any? = null): Observable<Any> {
        if (anyActivity is BaseActivity) {
            mCompositeDisposable.add(return RxView.clicks(view)
                .compose(RxTransformer.clickThrottle())
                .compose(anyActivity.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread()))
        } else if (anyActivity is BaseFragment) {
            mCompositeDisposable.add(return RxView.clicks(view)
                .compose(RxTransformer.clickThrottle())
                .compose(anyActivity.bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread()))
        }
        mCompositeDisposable.add(return RxView.clicks(view)
            .compose(RxTransformer.clickThrottle())
            .observeOn(AndroidSchedulers.mainThread()))

    }


    //textChanges事件
    fun textChanges(textView: TextView, anyActivity: Any? = null): Observable<CharSequence> {
        if (anyActivity is BaseActivity) {
            mCompositeDisposable.add(return RxTextView.textChanges(textView)
                    .compose(anyActivity.bindUntilEvent(ActivityEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread()))
        } else if (anyActivity is BaseFragment) {
            mCompositeDisposable.add(return RxTextView.textChanges(textView)
                    .compose(anyActivity.bindUntilEvent(FragmentEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread()))
        }
        mCompositeDisposable.add(return RxTextView.textChanges(textView)
                .observeOn(AndroidSchedulers.mainThread()))

    }

    /**
     * 延迟操作 总时间time 间隔是每一秒
     */
    fun delayDo(time: Long, anyActivity: Any? = null): Observable<Any> {
        if (anyActivity is BaseActivity) {
            mCompositeDisposable.add(return Observable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(anyActivity?.bindUntilEvent(ActivityEvent.DESTROY)))
        } else if (anyActivity is BaseFragment) {
            mCompositeDisposable.add(return Observable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(anyActivity?.bindUntilEvent(FragmentEvent.DESTROY)))
        }
        mCompositeDisposable.add(return Observable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { })
    }

    /**
     *轮询操作 间隔时间是time
     */
    fun pollingDo(time: Long, anyActivity: Any? = null): Observable<Any> {
        if (anyActivity is BaseActivity) {
            mCompositeDisposable.add(return Observable.interval(0, time, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(anyActivity?.bindUntilEvent(ActivityEvent.DESTROY)))
        } else if (anyActivity is BaseFragment) {
            mCompositeDisposable.add(return Observable.interval(0, time, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(anyActivity?.bindUntilEvent(FragmentEvent.DESTROY)))
        }
        mCompositeDisposable.add(return Observable.interval(0, time, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { })
    }
}