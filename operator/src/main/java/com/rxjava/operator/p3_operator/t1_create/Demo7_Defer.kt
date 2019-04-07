package com.rxjava.operator.p3_operator.t1_create

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable

/**
 * author : magic
 * date   : 13/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 作用
 * 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
 *
 * 通过 Observable工厂方法创建被观察者对象（Observable）
 * 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
 */
@Suppress("ClassName")
class Demo7_Defer : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        /**
         * 应用场景
         * 延迟指定事件，发送一个0，一般用于检测
         */
        var eventValue = 1

        val observable = Observable.defer(object : Callable<ObservableSource<out Int>> {

            override fun call(): ObservableSource<out Int> {
                return Observable.just(eventValue)
            }

        })

        //修改一次 eventValue的值
        eventValue = 5

        // 注：此时 subscribe()之后，才会调用defer() 创建被观察者对象（Observable）
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