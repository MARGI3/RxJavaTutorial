package com.rxjava.tutorial.p3_operator.t6_filter.codition

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate

@Suppress("ClassName")
class Demo3_SkipIndex : ItemRunnable() {

    /**
     * 跳过某个事件
     */
    override fun run() {
        super.run()

        val index = 3L

        val indexLast = 2

        Observable.just(1, "A", 3, "C", "D", "L", 7, 8)
                .skip(index)
                .skipLast(indexLast)
                .subscribe(object : Observer<Any> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "start subscribe")
                    }

                    override fun onNext(t: Any) {
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
         * skip(index) 跳过前 index 个发送的事件
         *
         * skipLast(indexLast) 跳过后 indexLast 个发送的事件
         */
    }
}