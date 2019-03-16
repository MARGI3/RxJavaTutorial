package com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

/**
 * issue: https://stackoverflow.com/questions/32131594/rx-java-mergedelayerror-not-working-as-expected
 *
 *
 */
@Suppress("ClassName")
class Demo4_MergeDelayError : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val observable1 = Observable.create(object : ObservableOnSubscribe<Long> {

            override fun subscribe(emitter: ObservableEmitter<Long>) {
                for (i in 1 .. 7) {
                    emitter.onNext(i.toLong())
                    Thread.sleep(1000)
                    //simulate a exception
                    if (i == 4) {
                        /** 发送Error事件，因为无使用 mergeDelayError，所以 当前时间点后续事件将不会发送事件 */
                        emitter.onError(IllegalStateException())
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())

        val observable2 = Observable.intervalRange(10, 10, 500, 500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

        val mergeObservable = Observable.merge(observable1, observable2)

//        mergeObservable.subscribe(object : Observer<Long> {
//
//            override fun onSubscribe(d: Disposable) {
//                Log.d(TAG, "---------------------- start1 -----------------------")
//                Log.d(TAG, "start subscribe time - ${System.currentTimeMillis()}")
//            }
//
//            override fun onNext(t: Long) {
//                Log.d(TAG, "receive event $t time - ${System.currentTimeMillis()}")
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "handle complete")
//                Log.d(TAG, "---------------------- onComplete1 -----------------------")
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "handle error $e")
//                Log.d(TAG, "---------------------- onError1 -----------------------")
//            }
//        })

        //可以对比这个代码块的运行结果

        val mergeDelayErrorObservable = Observable.mergeArrayDelayError(observable1, observable2)

        mergeDelayErrorObservable
            .subscribe(object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "---------------------- start2 -----------------------")
                Log.d(TAG, "delayError start subscribe time - ${System.currentTimeMillis()}")
            }

            override fun onNext(t: Long) {
                Log.d(TAG, "delayError receive event $t time - ${System.currentTimeMillis()}")
            }

            override fun onComplete() {
                Log.d(TAG, "delayError handle complete")
                Log.d(TAG, "---------------------- onComplete2 -----------------------")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "delayError handle error $e")
                Log.d(TAG, "---------------------- onError2 -----------------------")
            }
        })
    }

}