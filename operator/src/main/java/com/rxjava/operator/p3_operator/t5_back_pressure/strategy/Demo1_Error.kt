package com.rxjava.operator.p3_operator.t5_back_pressure.strategy

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


/**
 * 上游发送事件速度 > 下游消耗事件的速度 (流速不匹配)
 *
 * 缓存区已经存满, 上游仍然在继续发送事件
 *
 * 采用何种策略处理以上的问题
 */
@Suppress("ClassName")
class Demo1_Error : ItemRunnable() {

    override fun run() {
        super.run()
        Flowable.create(FlowableOnSubscribe<Int> {

            for (i in 1..129) {
                Log.d(TAG, "send event $i")
                it.onNext(i)
            }

            it.onComplete()

        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Int> {

                override fun onSubscribe(s: Subscription?) {
                    Log.d(TAG, "onSubscribe $s")
                }

                override fun onNext(t: Int?) {
                    Log.d(TAG, "onNext : $t")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError : $t")
                }
            })

        /**
         * 直接抛出异常
         */

        //  D/RxJavaTutorial: onSubscribe 0
        //  D/RxJavaTutorial: send event 1
        //  D/RxJavaTutorial: send event 2
        //  D/RxJavaTutorial: send event 3
        //  D/RxJavaTutorial: send event 4
        //  D/RxJavaTutorial: send event 5
        //  D/RxJavaTutorial: send event 6
        //  D/RxJavaTutorial: send event 7
        //  D/RxJavaTutorial: send event 8
        //  D/RxJavaTutorial: send event 9
        //  ....
        //  D/RxJavaTutorial: send event 125
        //  D/RxJavaTutorial: send event 126
        //  D/RxJavaTutorial: send event 127
        //  D/RxJavaTutorial: send event 128
        //  D/RxJavaTutorial: send event 129
        //  E/RxJavaTutorial: onError : io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
    }
}