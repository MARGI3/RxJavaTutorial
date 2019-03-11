package com.rxjava.tutorial.p2_thread_scheduler

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
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
class SchedulerDemo2 : ItemRunnable() {

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

        @Suppress("ObjectLiteralToLambda")
        val disposable = observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        Log.d(TAG, "doOnNext After mainThread : ${Thread.currentThread().name}")
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        Log.d(TAG, "doOnNext After io : ${Thread.currentThread().name}")
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)

        /**
         * 多次指定上游的线程只有第一次指定的有效,
         * 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略
         *
         *
         * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次
         */


        //RxJavaTutorial: Observable thread is : RxNewThreadScheduler-4
        //RxJavaTutorial: emit 1
        //RxJavaTutorial: doOnNext After mainThread : main
        //RxJavaTutorial: doOnNext After io : RxCachedThreadScheduler-3
        //RxJavaTutorial: Observable thread is : main
        //RxJavaTutorial: onNext: 1

    }
}