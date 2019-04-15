package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import java.util.concurrent.TimeUnit

/**
 * 阻塞 observable1 的事件 直到 observable2 发送事件，observable1 才开始发送事件
 */
class Demo6SkipUntil : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()

        //间隔 1s 发送一个事件
        val observable1 = Observable.interval(1, TimeUnit.SECONDS)

        //delay 4s 发送一个事件
        val observable2 = Observable.timer(3, TimeUnit.SECONDS)

        observable1.observeOn(IoScheduler())
            .skipUntil(observable2.observeOn(IoScheduler()))
            .subscribe(object : Observer<Long> {

                private lateinit var disposable: Disposable

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    Log.d(TAG, "start subscribe time - ${System.currentTimeMillis() / 1000}")
                }

                override fun onNext(t: Long) {
                    if (t >= 10) {
                        disposable.dispose()
                    }
                    Log.d(TAG, "receive event $t time - ${System.currentTimeMillis() / 1000}")
                }

                override fun onComplete() {
                    Log.d(TAG, "handle complete")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "handle error $e")
                }
            })

        //RxJavaTutorial: start subscribe time - 1553589194
        //RxJavaTutorial: receive event 3 time - 1553589198
        //RxJavaTutorial: receive event 4 time - 1553589199
        //RxJavaTutorial: receive event 5 time - 1553589200
        //RxJavaTutorial: receive event 6 time - 1553589201
        //RxJavaTutorial: receive event 7 time - 1553589202
        //RxJavaTutorial: receive event 8 time - 1553589203
        //RxJavaTutorial: receive event 9 time - 1553589204
        //RxJavaTutorial: receive event 10 time - 1553589205
    }
}