package com.rxjava.operator.p3_operator.t1_create

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 12/03/2019
 * mail   : 562224864cross@gmail.com
 */
@Suppress("ClassName")
class Demo5_FromIterable : ItemRunnable() {

    override fun run() {
        super.run()

        val list = listOf(1, 2, 3, 4)

        val observable = Observable.fromIterable(list)

        observable.subscribe(object : Observer<Int> {

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
    }

}