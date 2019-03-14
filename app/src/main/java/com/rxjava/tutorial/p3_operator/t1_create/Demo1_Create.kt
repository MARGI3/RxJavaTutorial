package com.rxjava.tutorial.p3_operator.t1_create

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 11/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 需求场景 完整的创建被观察者对象
 */
class Demo1_Create : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val observable = Observable.create(object : ObservableOnSubscribe<Int> {

            // 传入参数： OnSubscribe 对象
            // 当 Observable 被订阅时，OnSubscribe 的 call() 方法会自动被调用，即事件序列就会依照设定依次被触发
            // 即观察者会依次调用对应事件的复写方法从而响应事件
            // 从而实现由被观察者向观察者的事件传递 & 被观察者调用了观察者的回调方法 ，即观察者模式

            override fun subscribe(emitter: ObservableEmitter<Int>) {
                // 通过 ObservableEmitter类对象 产生 & 发送事件
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
                emitter.onComplete()
            }

        })

        observable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t")
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