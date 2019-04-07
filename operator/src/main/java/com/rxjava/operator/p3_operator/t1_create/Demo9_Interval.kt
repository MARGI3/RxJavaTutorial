package com.rxjava.operator.p3_operator.t1_create

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * 作用
 * 1. 快速创建1个被观察者对象（Observable）
 * 2. 发送事件的特点：每隔指定时间 就发送 事件
 *
 * 发送的事件序列 = 从0开始、无限递增1的的整数序列
 *
 */
@Suppress("ClassName")
class Demo9_Interval : ItemRunnable() {

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        /**
         * 参数说明：
         * 参数1 = 第1次延迟时间；
         * 参数2 = 间隔时间数字；
         * 参数3 = 时间单位；
         */
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {

                    private lateinit var disposable: Disposable

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        Log.d(TAG, "start subscribe time - ${System.currentTimeMillis() / 1000}")
                    }

                    override fun onNext(t: Long) {
                        if (t > 10) {
                            disposable.dispose()
                        }
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
         * interval默认在computation调度器上执行
         *
         * 通过 interval() Scheduler参数指定线程
         */
        Observable.interval(3, 1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
    }

}