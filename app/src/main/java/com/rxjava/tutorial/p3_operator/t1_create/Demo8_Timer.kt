package com.rxjava.tutorial.p3_operator.t1_create

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
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
 * 发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
 *
 * 本质 = 延迟指定时间后，调用一次 onNext(0)
 * 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
 */
class Demo8_Timer : ItemRunnable() {

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        /**
         * 应用场景
         * 动态创建被观察者对象（Observable） & 获取最新的Observable对象数据
         */
        // 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "start subscribe time - ${System.currentTimeMillis() / 1000}")
                    }

                    override fun onNext(t: Long) {
                        Log.d(TAG, "receive event $t time - ${System.currentTimeMillis() / 1000}")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "handle complete")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "handle error ${e.message}")
                    }
                })


        /**
         * timer操作符默认运行在一个新线程上
         * 通过 timer() Scheduler 参数指定线程
         */
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
    }

}