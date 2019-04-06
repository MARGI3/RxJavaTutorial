package com.rxjava.tutorial.p3_operator.t6_filter.codition

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

@Suppress("ClassName")
class Demo4_SkipTime : ItemRunnable() {

    /**
     * 跳过某个事件
     */
    override fun run() {
        super.run()

        Observable.intervalRange(0, 6, 0, 1, TimeUnit.SECONDS)
                .skip(1500, TimeUnit.MILLISECONDS)
                .skipLast(2, TimeUnit.SECONDS)
                .subscribe(object : Observer<Any> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "start subscribe")
                    }

                    override fun onNext(t: Any) {
                        Log.d(TAG, "receive event $t")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "handle complete")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "handle error ${e.message}")
                    }
                })

        /**
         * skip(time1, TimeUnit) 跳过前 time1 时间发送的事件
         *
         * skipLast(time2, TimeUnit) 跳过后 time2 时间发送的事件
         */
    }
}