package com.rxjava.tutorial.p1_basic_usage

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class BasicUsageDemo : ItemRunnable() {

//    override fun run() {
//
//        val observable = Observable.create(ObservableOnSubscribe<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//            it.onComplete()
//        })
//
//        val observer = object : Observer<Int> {
//
//            override fun onComplete() {
//                Log.d(TAG, "onComplete")
//            }
//
//            override fun onSubscribe(d: Disposable) {
//                Log.d(TAG, "onSubscribe")
//            }
//
//            override fun onNext(t: Int) {
//                Log.d(TAG, "onNext $t")
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "onError")
//            }
//
//        }
//
//        observable.subscribe(observer)
//    }

    @Suppress("ObjectLiteralToLambda")
    override fun run() {

        /**
         * 只有当上游和下游建立连接之后, 上游才会开始发送事件.
         * 也就是调用了subscribe() 方法之后才开始发送事件
         */


        //链式调用
        Observable.create(object : ObservableOnSubscribe<Int> {

            /**
             * ObservableEmitter  onNext() onComplete() onError()
             *
             * 1. onNext() 可以发送无数次，下游也可以无限接收
             *
             * 2. onComplete() 调用之后，上游还可以继续发送事件，但是下游接收到 onComplete() 事件之后 就不会接收事件
             *
             * 3. onError() 调用之后，上游还可以继续发送事件，但是下游接收到 onError() 事件之后 就不会接收事件
             *
             * 4. 上游可以不发送onComplete或onError
             *
             * 5. 最为关键的是onComplete和onError必须唯一并且互斥,
             *    即不能发多个onComplete, 也不能发多个onError, 也不能先发一个onComplete,
             *    然后再发一个onError, 反之亦然
             */

            //关于onComplete和onError唯一并且互斥这一点,
            // 是需要自行在代码中进行控制, 如果你的代码逻辑中违背了这个规则,
            // 并不一定会导致程序崩溃. 比如发送多个onComplete是可以正常运行的,
            // 依然是收到第一个onComplete就不再接收了,
            // 但若是发送多个onError, 则收到第二个onError事件会导致程序会崩溃

            /**
             * onError() onComplete() 是上游决定数据流是否结束的方法
             */
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(4)
                emitter.onNext(5)
                emitter.onNext(6)
                emitter.onComplete()
                emitter.onNext(7)
                emitter.onNext(8)
            }

        }).subscribe(object : Observer<Int> {

            /**
             * 我们可以把它理解成两根管道之间的一个机关,
             * 当调用它的dispose()方法时, 它就会将数据流管道切断, 从而导致下游收不到事件.
             *
             * 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件.
             *
             * dispose() 是下游决定数据流是否结束的方法
             */
            private lateinit var mDisposable: Disposable

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "onNext $t")

                if (t == 5) {
                    Log.d(TAG, "dispose")
                    mDisposable.dispose()
                    Log.d(TAG, "isDisposed : ${mDisposable.isDisposed}")
                }
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError")
            }

        })
    }
}