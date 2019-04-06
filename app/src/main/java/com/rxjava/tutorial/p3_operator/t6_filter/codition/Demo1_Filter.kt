package com.rxjava.tutorial.p3_operator.t6_filter.codition

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate

@Suppress("ClassName")
class Demo1_Filter : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
            .filter(object : Predicate<Int> {

                override fun test(t: Int): Boolean {
                    //return true send event
                    //return false skip send event
                    return t % 2 == 0
                }

            }).subscribe(object : Observer<Int> {

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