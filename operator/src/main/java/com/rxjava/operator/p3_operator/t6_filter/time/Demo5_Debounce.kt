package com.rxjava.operator.p3_operator.t6_filter.time

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@Suppress("ClassName")
class Demo5_Debounce : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()

        Observable.create(ObservableOnSubscribe<Int> {

            it.onNext(1)
            Thread.sleep(500)

            //event1 event2 interval < 1s , event1 was dropped

            it.onNext(2)
            Thread.sleep(300)

            //event2 event3 interval < 1s , event2 was dropped

            it.onNext(3)
            Thread.sleep(500)

            //event3 event4 interval < 1s , event3 was dropped

            it.onNext(4)
            Thread.sleep(1200)

            //event4 event5 interval > 1s , event4 was sent

            it.onNext(5)
            Thread.sleep(500)

            //event5 event6 interval < 1s , event5 was dropped

            it.onNext(6)
            Thread.sleep(1200)

            //event6 event7 interval > 1s , event6 was sent

            it.onNext(7)
            Thread.sleep(300)

            //event7 event8 interval < 1s , event7 was dropped

            it.onNext(8)
            Thread.sleep(300)

            it.onComplete()

            //onComplete event8 was sent

        }).debounce(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

        /**
         * 发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，
         * 直到指定时间内都没有新数据发射时才会发送后一次的数据
         */

        //RxJavaTutorial: start subscribe
        //RxJavaTutorial: receive event 4
        //RxJavaTutorial: receive event 6
        //RxJavaTutorial: receive event 8
        //RxJavaTutorial: handle complete
    }
}