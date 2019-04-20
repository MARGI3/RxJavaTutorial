package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * 关联两个Observable 情况
 *
 * observable1 正常发送事件  直到observable2发送事件 则停止 observable1 的事件发送
 */
class Demo5TakeUntil2 : ItemRunnable() {

    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        //间隔 1s 发送一个事件
        val observable1 = Observable.interval(1, TimeUnit.SECONDS)

        //delay 4s 发送一个事件
        val observable2 = Observable.timer(4, TimeUnit.SECONDS)

        observable1.takeUntil(observable2)
            .subscribe(object : Observer<Long> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe , current time : ${System.currentTimeMillis()}")
                }

                override fun onNext(t: Long) {
                    Log.d(TAG, "onNext $t , current time : ${System.currentTimeMillis()}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete , current time : ${System.currentTimeMillis()}")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError $e")
                }
            })

        /**
         *
         * discard any items emitted by an Observable after a second Observable emits an item or terminates
         *
         * observable1 正常发送事件  直到observable2发送事件 则停止 observable1 的事件发送
         */

        //RxJavaTutorial: onSubscribe , current time : 1553655977436
        //RxJavaTutorial: onNext 0 , current time : 1553655978441
        //RxJavaTutorial: onNext 1 , current time : 1553655979441
        //RxJavaTutorial: onNext 2 , current time : 1553655980441
        //RxJavaTutorial: onComplete , current time : 1553655981441
    }
}