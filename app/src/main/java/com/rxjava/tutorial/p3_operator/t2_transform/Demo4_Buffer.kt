package com.rxjava.tutorial.p3_operator.t2_transform

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 14/03/2019
 * mail   : 562224864cross@gmail.com
 *
 * periodically gather items from an Observable into bundles
 *
 * and emit these bundles rather than emitting the items one at a time
 *
 * 定期将event打包
 *
 * 然后一起发送打包后的events （相比于原始的 一次发送一个）
 *
 */
@Suppress("ClassName")
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
         * transform Observable<T> to Observable<List<T>>
         * 按照规则将上游事件打包，然后转换成 新的 Observable
         */

        /**
         *
         * first buffer:  count = 3
         *                start from No.1 item
         *                1, 2, 3 put into buffer area, then emit event 1, 2, 3
         *
         * second buffer: count = 3
         *                skip = 5
         *                because skip = 5, so start from No.1 + 5 = No.6 item
         *                6, 7, 8 put into buffer area, then emit event 6, 7, 8
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

        //RxJavaTutorial: start subscribe
        //RxJavaTutorial: ------ buffer size = 3
        //RxJavaTutorial:  event value = 1
        //RxJavaTutorial:  event value = 2
        //RxJavaTutorial:  event value = 3
        //RxJavaTutorial: ------ buffer size = 3
        //RxJavaTutorial:  event value = 6
        //RxJavaTutorial:  event value = 7
        //RxJavaTutorial:  event value = 8
        //RxJavaTutorial: ------ buffer size = 2
        //RxJavaTutorial:  event value = 11
        //RxJavaTutorial:  event value = 12
        //RxJavaTutorial: handle complete
    }

}