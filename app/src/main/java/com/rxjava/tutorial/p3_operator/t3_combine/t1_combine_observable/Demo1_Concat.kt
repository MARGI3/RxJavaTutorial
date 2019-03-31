package com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
 */
@Suppress("ClassName")
class Demo1_Concat : ItemRunnable() {

    override fun run() {
        super.run()
        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.just(5, 6, 7)
        val observable3 = Observable.just(8, 9, 10)

        /**
         * concat() method can combine (size <= 4) observables
         *
         * and they will execute by serial according to the concat observables event emmit order
         *
         * 根据concat observable 的发送顺序串行执行
         */
        val concatObservable = Observable.concat(observable1, observable2, observable3)

        concatObservable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error ${e.message}")
            }
        })

        /**
         * concat() method can combine (size <= 4 || size > 4) observables
         *
         * and they will execute by serial according to the concatArray observables event emmit order
         *
         * 根据concatArray observable 的发送顺序串行执行
         */
        val observable4 = Observable.just(11, 12)
        val observable5 = Observable.just(13, 14)
        val observable6 = Observable.just(15, 16)
        val concatArrayObservable = Observable.concatArray(observable1, observable2, observable3, observable4, observable5, observable6)


    }
}