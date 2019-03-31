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
 * 作用
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：连续发送 1个事件序列，可指定范围
 *
 * a. 发送的事件序列 默认从0开始、无限递增1的的整数序列
 * b. 作用类似于intervalRange（），但区别在于：无延迟发送事件
 */
class Demo11_Range : ItemRunnable() {

    override fun run() {
        super.run()

        /**
         * start          事件起始值
         * count          事件数量
         */
        val observable = Observable.range(4, 10)

        observable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe time - ${System.currentTimeMillis() / 1000}")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t time - ${System.currentTimeMillis() / 1000}")
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