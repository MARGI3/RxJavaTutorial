package com.rxjava.tutorial.p3_operator.t6_filter.time

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@Suppress("ClassName")
class Demo3_Sample : ItemRunnable() {

    /**
     * 过滤 特定条件的事件
     */
    override fun run() {
        super.run()

        Observable.create(ObservableOnSubscribe<Int> {

            //0

            it.onNext(1)
            Thread.sleep(500)

            it.onNext(2)
            Thread.sleep(300)

            it.onNext(3)
            Thread.sleep(300)

            //1000

            it.onNext(4)
            Thread.sleep(400)

            it.onNext(5)
            Thread.sleep(500)

            //2000

            it.onNext(6)
            Thread.sleep(200)

            it.onNext(7)
            Thread.sleep(300)

            it.onComplete()

            //3000


        }).sample(1, TimeUnit.SECONDS)
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
         * 在指定的采样周期内 默认取最后一个 事件
         * 可以通过重载方法调整参数
         */

        //RxJavaTutorial: start subscribe
        //RxJavaTutorial: receive event 3
        //RxJavaTutorial: receive event 5
        //RxJavaTutorial: handle complete
    }
}