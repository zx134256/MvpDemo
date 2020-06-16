package com.leshu.gamebox.base.http

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/**
 * Name: RetryWhenProcess
 * Author: xieganag
 * Email:
 * Date: 2017-03-13 16:17
 * //总共重试3次，重试间隔3000毫秒
 */
class RetryWhenProcess(private val maxRetries: Int, private val retryDelayMillis: Int) : Function<Observable<Throwable>, ObservableSource<*>> {
    private var retryCount: Int = 0


    @Throws(Exception::class)
    override fun apply(throwableObservable: Observable<Throwable>): ObservableSource<*> {
        return throwableObservable.flatMap {
            if (++retryCount <= maxRetries) {
                Observable.timer(retryDelayMillis.toLong(),
                        TimeUnit.MILLISECONDS)
            } else Observable.error<Any>(Throwable("网络异常"))
        }
    }
}
