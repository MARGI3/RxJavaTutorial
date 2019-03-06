package com.rxjava.tutorial.p4_zip_function

import com.rxjava.tutorial.ItemRunnable
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.internal.schedulers.IoScheduler

class ZipOperatorDemo : ItemRunnable() {

    companion object {
        private const val TAG = "ZipOperatorDemo"
    }

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val observable1 = Observable.create(object : ObservableOnSubscribe<Int> {

            override fun subscribe(emitter: ObservableEmitter<Int>) {
                Thread.sleep(1000)
                Log.d(TAG, "${Thread.currentThread().name} * emit 1")
                emitter.onNext(1)
                Log.d(TAG, "${Thread.currentThread().name} * emit 2")
                emitter.onNext(2)
                Log.d(TAG, "${Thread.currentThread().name} * emit 3")
                emitter.onNext(3)
                Log.d(TAG, "${Thread.currentThread().name} * emit 4")
                emitter.onNext(4)
                Log.d(TAG, "${Thread.currentThread().name} * emit 5")
                emitter.onNext(5)
                Log.d(TAG, "${Thread.currentThread().name} * emit 6")
                emitter.onNext(6)
                Log.d(TAG, "${Thread.currentThread().name} * emit 7")
                emitter.onNext(7)
                Log.d(TAG, "${Thread.currentThread().name} * emit complete1")
                emitter.onComplete()
            }

        }).subscribeOn(IoScheduler())

        val observable2 = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(emitter: ObservableEmitter<String>) {
                Log.d(TAG, "${Thread.currentThread().name} : emit A")
                emitter.onNext("A")
                Thread.sleep(1000)
                Log.d(TAG, "${Thread.currentThread().name} : emit B")
                emitter.onNext("B")

                //如果当前任务出现异常
                // 1. 则当前 observable2 后续事件丢失
                // 2. zip observable3 后续 zip 事件无法完成 直接收到 onError() 回调
                // 3. observable1 后续事件正常发送 不受影响
//                throw Exception()

                Log.d(TAG, "${Thread.currentThread().name} : emit C")
                emitter.onNext("C")
                Log.d(TAG, "${Thread.currentThread().name} : emit complete2")
                emitter.onComplete()
            }

        }).subscribeOn(IoScheduler())

        val observable3 = Observable.zip(observable1, observable2, object : BiFunction<Int, String, String> {

            override fun apply(t1: Int, t2: String): String {
                val result = t2 + t1
                Log.d(TAG, "${Thread.currentThread().name} # zip function $result")
                return result
            }
        })

        observable3.subscribe(object : Observer<String> {

            override fun onComplete() {
                Log.d(TAG, "operate onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "operate onSubscribe")
            }

            override fun onNext(t: String) {
                Log.d(TAG, "operate onNext $t")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "operate onError $e")
            }
        })

        /**
         * 适用场景: 多个并发任务，需要等待 多个任务的结果才能进行下一步操作
         */


        /**
         * 如果 observable3 不指定 subscribeOn 的线程，则默认采用 observable1 observable2 中后完成的线程来执行
         *
         * eg:
         *
         *            thread1        thread2
         *          observable1     observable2      zip
         *
         * event1     先完成            后完成        apply 在 thread2 中执行
         *
         * event2     后完成            先完成        apply 在 thread1 中执行
         *
         */

        /**
         * 上游 两个数据流的 event 数量不一致时， 下游 zip event 数量采用上游数据中最小值
         *
         * A 7 个 event
         * B 4 个 event
         * zip 操作只会触发4次
         */

        //ZipOperatorDemo: operate onSubscribe
        //ZipOperatorDemo: RxCachedThreadScheduler-4 : emit A
        //ZipOperatorDemo: RxCachedThreadScheduler-3 * emit 1
        //ZipOperatorDemo: RxCachedThreadScheduler-3 # zip function A1
        //ZipOperatorDemo: operate onNext A1
        //ZipOperatorDemo: RxCachedThreadScheduler-3 * emit 2
        //ZipOperatorDemo: RxCachedThreadScheduler-3 * emit 3
        //ZipOperatorDemo: RxCachedThreadScheduler-3 * emit 4
        //ZipOperatorDemo: RxCachedThreadScheduler-3 * emit complete1
        //ZipOperatorDemo: RxCachedThreadScheduler-4 : emit B
        //ZipOperatorDemo: RxCachedThreadScheduler-4 # zip function B2
        //ZipOperatorDemo: operate onNext B2
        //ZipOperatorDemo: RxCachedThreadScheduler-4 : emit C
        //ZipOperatorDemo: RxCachedThreadScheduler-4 # zip function C3
        //ZipOperatorDemo: operate onNext C3
        //ZipOperatorDemo: RxCachedThreadScheduler-4 : emit complete2
        //ZipOperatorDemo: operate onComplete
        //ZipOperatorDemo: RxCachedThreadScheduler-1 * emit 4
        //ZipOperatorDemo: RxCachedThreadScheduler-1 * emit 5
        //ZipOperatorDemo: RxCachedThreadScheduler-1 * emit 6
        //ZipOperatorDemo: RxCachedThreadScheduler-1 * emit 7
        //ZipOperatorDemo: RxCachedThreadScheduler-1 * emit complete1
    }
}