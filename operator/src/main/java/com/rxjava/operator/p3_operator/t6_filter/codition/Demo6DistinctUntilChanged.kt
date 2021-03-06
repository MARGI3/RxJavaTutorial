package com.rxjava.operator.p3_operator.t6_filter.codition

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class Demo6DistinctUntilChanged : ItemRunnable() {

    /**
     * 跳过某个事件
     */
    override fun run() {
        super.run()

        Observable.just(1, 2, 3, 1, 2, 3, 3, 4, 4)
                .distinctUntilChanged()
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
         * 移除 连续重复 event
         */

        //RxJavaTutorial: start subscribe
        //RxJavaTutorial: receive event 1
        //RxJavaTutorial: receive event 2
        //RxJavaTutorial: receive event 3
        //RxJavaTutorial: receive event 1
        //RxJavaTutorial: receive event 2
        //RxJavaTutorial: receive event 3
        //RxJavaTutorial: receive event 4
        //RxJavaTutorial: handle complete
    }
}