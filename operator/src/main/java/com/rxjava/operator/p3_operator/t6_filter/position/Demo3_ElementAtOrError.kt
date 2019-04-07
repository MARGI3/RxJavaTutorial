package com.rxjava.operator.p3_operator.t6_filter.position

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

@Suppress("ClassName")
class Demo3_ElementAtOrError : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()

        Observable.just(1, 2, 3, 4, 5, 6, 7)
            .elementAtOrError(10)
            .subscribe(object : SingleObserver<Int> {

                override fun onSuccess(t: Int) {
                    Log.d(TAG, "element at out of bounce onSuccess $t")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "element at out of bounce start subscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "element at out of bounce handle error $e")
                }
            })

        /**
         * 取对应位置的事件 (可以越界获取) 获取不到则触发异常
         */
        //RxJavaTutorial: element at out of bounce start subscribe
        //RxJavaTutorial: element at out of bounce handle error java.util.NoSuchElementException
    }
}