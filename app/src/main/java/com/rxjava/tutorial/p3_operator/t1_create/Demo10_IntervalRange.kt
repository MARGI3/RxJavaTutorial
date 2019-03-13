package com.rxjava.tutorial.p3_operator.t1_create

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * author : magic
 * date   : 13/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量
 *
 * a. 发送的事件序列 默认从0开始、无限递增1的的Long序列
 * b. 作用类似于interval（），但可指定发送的数据的数量
 */
class Demo10_IntervalRange : ItemRunnable() {

    override fun run() {
        super.run()

        /**
         * start          事件起始值
         * count          事件数量
         * initialDelay   subscribe到第一个事件发布，事件间隔
         * period         间隔时间长度
         * TimeUnit       事件单位
         */
        val observable = Observable.intervalRange(4, 10, 500, 1000, TimeUnit.MILLISECONDS)

        observable.subscribe(object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe time - ${System.currentTimeMillis()}")
            }

            override fun onNext(t: Long) {
                Log.d(TAG, "receive event $t time - ${System.currentTimeMillis()}")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error ${e.message}")
            }
        })
    }

}