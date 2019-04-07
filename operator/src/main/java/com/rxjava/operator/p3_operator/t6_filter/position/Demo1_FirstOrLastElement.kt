package com.rxjava.operator.p3_operator.t6_filter.position

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

@Suppress("ClassName")
class Demo1_FirstOrLastElement : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()

        Observable.just(1, 2, 3, 4, 5, 6, 7)
            .firstElement()
            .subscribe(object : MaybeObserver<Int> {

                override fun onSuccess(t: Int) {
                    Log.d(TAG, "first element onSuccess $t")
                }

                override fun onComplete() {
                    Log.d(TAG, "first element handle complete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "first element start subscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "first element handle error $e")
                }

            })

        Observable.just(1, 2, 3, 4, 5, 6, 7)
            .lastElement()
            .subscribe(object : MaybeObserver<Int> {

                override fun onSuccess(t: Int) {
                    Log.d(TAG, "last element onSuccess $t")
                }

                override fun onComplete() {
                    Log.d(TAG, "last element handle complete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "last element start subscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "last element handle error $e")
                }

            })

        /**
         * 取 第一个 or 最后一个 发送的事件
         */

        //RxJavaTutorial: first element start subscribe
        //RxJavaTutorial: first element onSuccess 1
        //RxJavaTutorial: last element start subscribe
        //RxJavaTutorial: last element onSuccess 7
    }
}