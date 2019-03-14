package com.rxjava.tutorial.p3_operator.t2_change

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 14/03/2019
 * mail   : 562224864cross@gmail.com
 */
class Demo4_Buffer : ItemRunnable() {

    override fun run() {
        super.run()

        val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

        val observable = Observable.fromArray(*array)

        observable.buffer(3, 5).subscribe(object : Observer<List<Int>> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(list: List<Int>) {
                Log.d(ItemRunnable.TAG, "------ buffer size = " + list.size)
                for (value in list) {
                    Log.d(ItemRunnable.TAG, " event value = $value")
                }
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error ${e.message}")
            }

        })

        /**
         *
         * first buffer:  count = 3
         *                start from No.1 item
         *                1, 2, 3 put into buffer area, then emit event 1, 2, 3
         *
         * second buffer: count = 3
         *                skip = 5
         *                because skip = 5, so start from No.1 + 5 = No.6 item
         *                6, 7, 8 put into buffer area, then emit event 1, 2, 3
         *
         * third buffer: count = 3
         *               skip = 5
         *               because skip = 5, so start from No.6 + 5 = No.11
         *               11, 12 put into buffer area, then emit event 11, 12
         *
         *
         *
         *
         */
    }

}