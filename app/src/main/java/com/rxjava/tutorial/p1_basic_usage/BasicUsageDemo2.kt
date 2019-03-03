package com.rxjava.tutorial.p1_basic_usage

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer


/**
 * subscribe() 方法的不同参数
 */
class BasicUsageDemo2 : ItemRunnable() {

    companion object {
        private const val TAG = "BasicUsageDemo2"
    }

    override fun run() {

        /**
         * 没有观察者，不关心数据接收时的回调
         *
         * public final Disposable subscribe() {}
         */
        val disposable1 = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }).subscribe()

        /**
         * 只关心 onNext() 事件的回调
         *
         * public final Disposable subscribe(Consumer<? super T> onNext) {}
         */
        @Suppress("ObjectLiteralToLambda")
        val disposable2 = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }).subscribe(object : Consumer<Int> {
            override fun accept(t: Int?) {
                Log.d(TAG, "onNext: $t")
            }
        })

        /**
         * 关心 onNext() onError() 事件的回调
         *
         * public final Disposable subscribe(Consumer<? super T> onNext,
         *                                   Consumer<? super Throwable> onError) {}
         */
        @Suppress("ObjectLiteralToLambda")
        val disposable3 = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onError(IllegalAccessException())
        }).subscribe(object : Consumer<Int> {
            override fun accept(t: Int?) {
                Log.d(TAG, "onNext: $t")
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.d(TAG, "onError: $t")
            }
        })


        /**
         * 关心 onNext() onError() onComplete() 事件的回调
         *
         * public final Disposable subscribe(Consumer<? super T> onNext,
         *                                   Consumer<? super Throwable> onError,
         *                                   Action onComplete) {}
         */
        @Suppress("ObjectLiteralToLambda")
        val disposable4 = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
//            it.onError(IllegalAccessException())
            it.onComplete()
        }).subscribe(object : Consumer<Int> {
            override fun accept(t: Int?) {
                Log.d(TAG, "onNext: $t")
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.d(TAG, "onError: $t")
            }
        }, object : Action {
            override fun run() {
                Log.d(TAG, "onComplete")
            }
        })


        /**
         * 关心 onNext() onError() onComplete() 事件的回调
         *
         * public final Disposable subscribe(Consumer<? super T> onNext,
         *                                   Consumer<? super Throwable> onError,
         *                                   Action onComplete,
         *                                   Consumer<? super Disposable> onSubscribe) {}
         */
        @Suppress("ObjectLiteralToLambda")
        val disposable5 = Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
//            it.onError(IllegalAccessException())
            it.onComplete()
        }).subscribe(object : Consumer<Int> {
            override fun accept(t: Int?) {
                Log.d(TAG, "onNext: $t")
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.d(TAG, "onError: $t")
            }
        }, object : Action {
            override fun run() {
                Log.d(TAG, "onComplete")
            }
        }, object : Consumer<Disposable> {
            override fun accept(t: Disposable?) {
                /**
                 * subscribe() 方法调用的时候 就会接收到回调，比onNext() onComplete() onError()早
                 */
                Log.d(TAG, "Disposable")
            }
        })
    }
}