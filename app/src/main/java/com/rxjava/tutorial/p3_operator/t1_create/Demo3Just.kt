package com.rxjava.tutorial.p3_operator.t1_create

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 11/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 *
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：直接发送 传入的事件
 *
 * 快速创建 被观察者对象（Observable） & 发送10个以下事件
 */
class Demo3Just : ItemRunnable() {

    override fun run() {
        super.run()

        // 1. 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)

        //注：最多只能发送10个参数  maximum is 10
        val observable = Observable.just(1, 2, 3, 4)

        observable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error")
            }
        })
    }

}