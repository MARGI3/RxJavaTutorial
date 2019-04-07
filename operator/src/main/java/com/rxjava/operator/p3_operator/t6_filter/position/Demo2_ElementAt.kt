package com.rxjava.operator.p3_operator.t6_filter.position

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

@Suppress("ClassName")
class Demo2_ElementAt : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()

        Observable.just(1, 2, 3, 4, 5, 6, 7)
            .elementAt(5)
            .subscribe(object : MaybeObserver<Int> {

                override fun onSuccess(t: Int) {
                    Log.d(TAG, "element at onSuccess $t")
                }

                override fun onComplete() {
                    Log.d(TAG, "element at handle complete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "element at start subscribe")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "element at handle error ${e.message}")
                }

            })

        Observable.just(1, 2, 3, 4, 5, 6, 7)
            .elementAt(10, -1)
            .subscribe(object : SingleObserver<Int> {

                override fun onSuccess(t: Int) {
                    Log.d(TAG, "element at out of bounce onSuccess $t")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "element at out of bounce start subscribe")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "element at out of bounce handle error ${e.message}")
                }
            })

        /**
         * 取对应位置的事件 (可以越界获取)
         */

        //RxJavaTutorial: element at start subscribe
        //RxJavaTutorial: element at onSuccess 4
        //RxJavaTutorial: element at out of bounce start subscribe
        //RxJavaTutorial: element at out of bounce onSuccess -1

        /**
         * 如果调用 elementAt 越界获取，但是没有指定 默认值
         * 直接触发onComplete()
         */
        //RxJavaTutorial: element at start subscribe
        //RxJavaTutorial: element at handle complete
    }
}