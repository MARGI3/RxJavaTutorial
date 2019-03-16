package com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
 */
class Demo3_Merge : ItemRunnable() {

    override fun run() {
        super.run()

        val observable1 = Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
        val observable2 = Observable.intervalRange(5, 3, 500, 500, TimeUnit.MILLISECONDS)

        /**
         * merge() method can combine (size <= 4) observables
         *
         * and they will execute by parallel according to the merge observables event emmit time order
         *
         * 根据merge observable 的发送时间并行执行
         */
        val mergeObservable = Observable.merge(observable1, observable2)

        mergeObservable.subscribe(object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe time - ${System.currentTimeMillis()}")
            }

            override fun onNext(t: Long) {
                Log.d(TAG, "receive event $t time - ${System.currentTimeMillis()}")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error $e")
            }
        })

        /**
         * merge() method can combine (size <= 4 || size > 4) observables
         *
         * and they will execute by parallel according to the merge observables event emmit time order
         *
         * 根据merge observable 的发送时间并行执行
         */
        val observable3 = Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
        val observable4 = Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
        val observable5 = Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
        val observable6 = Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
        val mergeArrayObservable = Observable.mergeArray(observable1, observable2, observable3, observable4, observable5, observable6)
    }
}