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
class Demo3Buffer : ItemRunnable() {

    override fun run() {
        super.run()
        Flowable.create(FlowableOnSubscribe<Int> {

            for (i in 1..150) {
                Log.d(TAG, "send event $i")
                it.onNext(i)
            }

            it.onComplete()

        }, BackpressureStrategy.BUFFER)
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
         * 将缓存区大小设置成无限大
         *
         * 1. 即 被观察者可无限发送事件 观察者，但实际上是存放在缓存区
         * 2. 但要注意内存情况，防止出现OOM
         */

        //RxJavaTutorial: onSubscribe 0
        //RxJavaTutorial: send event 1
        //RxJavaTutorial: send event 2
        //RxJavaTutorial: send event 3
        //RxJavaTutorial: send event 4
        //RxJavaTutorial: send event 5
        //RxJavaTutorial: send event 6
        //RxJavaTutorial: send event 7
        //RxJavaTutorial: send event 8
        //RxJavaTutorial: send event 9
        //...
        //RxJavaTutorial: send event 142
        //RxJavaTutorial: send event 143
        //RxJavaTutorial: send event 144
        //RxJavaTutorial: send event 145
        //RxJavaTutorial: send event 146
        //RxJavaTutorial: send event 147
        //RxJavaTutorial: send event 148
        //RxJavaTutorial: send event 149
        //RxJavaTutorial: send event 150

    }
}