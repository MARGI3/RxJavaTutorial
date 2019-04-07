package com.rxjava.operator.p2_thread_scheduler

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */
class SchedulerDemo : ItemRunnable() {

    override fun run() {
        super.run()
        val observable = Observable.create(ObservableOnSubscribe<Int> {
            Log.d(TAG, "Observable thread is : ${Thread.currentThread().name}")
            Log.d(TAG, "emit 1")
            it.onNext(1)
        })

        @Suppress("ObjectLiteralToLambda")
        val consumer = object : Consumer<Int> {
            override fun accept(t: Int?) {
                Log.d(TAG, "Observable thread is : ${Thread.currentThread().name}")
                Log.d(TAG, "onNext: $t")
            }
        }

        val disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)

        /**
         * subscribeOn() 指定上游事件发送线程
         * observeOn() 指定下游接收事件的线程
         */

        //RxJavaTutorial: Observable thread is : RxNewThreadScheduler-1
        //RxJavaTutorial: emit 1
        //RxJavaTutorial: Observable thread is : main
        //RxJavaTutorial: onNext: 1
    }
}