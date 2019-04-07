package com.rxjava.operator.p3_operator.t6_filter.size

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

@Suppress("ClassName")
class Demo1_Take : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .take(5)
                .subscribe(object : Observer<Int> {

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

        //上游依然发送了原始数量的事件，只是下游接收时 被过滤了。

        //RxJavaTutorial: start subscribe
        //RxJavaTutorial: receive event 1
        //RxJavaTutorial: receive event 2
        //RxJavaTutorial: receive event 3
        //RxJavaTutorial: receive event 4
        //RxJavaTutorial: receive event 5
        //RxJavaTutorial: handle complete
    }
}