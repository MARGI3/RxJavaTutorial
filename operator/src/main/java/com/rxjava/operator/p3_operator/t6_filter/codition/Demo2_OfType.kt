package com.rxjava.operator.p3_operator.t6_filter.codition

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

@Suppress("ClassName")
class Demo2_OfType : ItemRunnable() {

    /**
     * 过滤 特定数据类型的数据
     */
    override fun run() {
        super.run()
        Observable.just(1, "A", 3, "C", "D", "L", 7, 8)
            .ofType(String::class.java).subscribe(object : Observer<Any> {

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
    }
}