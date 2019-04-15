package com.rxjava.operator.p3_operator.t1_create

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 12/03/2019
 * mail   : 562224864cross@gmail.com
 */
class Demo6SpecialCreate : ItemRunnable() {

    override fun run() {
        super.run()

        // 下列方法一般用于测试使用

        // <-- empty()  -->
        // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
        val observable1 = Observable.empty<Int>()
        // 即观察者接收后会直接调用onCompleted（）
        observable1.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG + "1", "start subscribe")
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

        // <-- error()  -->
        // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
        // 可自定义异常
        val observable2 = Observable.error<Int>(RuntimeException())
        // 即观察者接收后会直接调用onError（）
        observable2.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG + "1", "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error $e")
            }
        })

        // <-- never()  -->
        // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
        val observable3 = Observable.never<Int>()
        // 即观察者接收后什么都不调用
        observable3.subscribe(object : Observer<Int> {

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