package com.rxjava.tutorial.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * 关联两个Observable 情况
 */
@Suppress("ClassName")
class Demo5_TakeUntil2 : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        //间隔 1s 发送一个事件
        val observable1 = Observable.interval(1, TimeUnit.SECONDS)

        //delay 4s 发送一个事件
        val observable2 = Observable.timer(4, TimeUnit.SECONDS)

        observable1.takeUntil(observable2)
            .subscribe(object : Consumer<Long> {

                override fun accept(t: Long) {
                    Log.d(TAG, "result $t , current time : ${System.currentTimeMillis()}")
                }
            })

        /**
         *
         * discard any items emitted by an Observable after a second Observable emits an item or terminates
         *
         * observable1 正常发送事件  直到observable2发送事件 则停止 observable1 的事件发送
         */

        //RxJavaTutorial: result 0 , current time : 1553587790439
        //RxJavaTutorial: result 1 , current time : 1553587791438
        //RxJavaTutorial: result 2 , current time : 1553587792438
    }
}