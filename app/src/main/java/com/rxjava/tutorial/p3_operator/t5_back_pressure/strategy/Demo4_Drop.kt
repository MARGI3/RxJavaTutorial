package com.rxjava.tutorial.p3_operator.t5_back_pressure.strategy

import android.util.Log
import android.view.View
import com.rxjava.tutorial.ITrigger
import com.rxjava.tutorial.ItemRunnable
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
class Demo4_Drop : ItemRunnable(), ITrigger {

    private var mSubscription: Subscription? = null

    override fun run() {
        super.run()
        Flowable.create(FlowableOnSubscribe<Int> {

            for (i in 1..150) {
                Log.d(TAG, "send event $i")
                it.onNext(i)
            }

            it.onComplete()

        }, BackpressureStrategy.DROP)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<Int> {

                override fun onSubscribe(subscription: Subscription?) {
                    Log.d(TAG, "onSubscribe $subscription")

                    mSubscription = subscription
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
         * 超过缓存区大小（128）的事件丢弃
         *
         * 如发送了150个事件，仅保存第1 - 第128个事件，第129 -第150事件将被丢弃
         */
    }

    override fun onTrigger(view: View?) {
        mSubscription?.request(128)
    }

    /**
     * Run
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
    //RxJavaTutorial: send event 10
    //RxJavaTutorial: send event 11
    //RxJavaTutorial: send event 12
    //....
    //RxJavaTutorial: send event 141
    //RxJavaTutorial: send event 142
    //RxJavaTutorial: send event 143
    //RxJavaTutorial: send event 144
    //RxJavaTutorial: send event 145
    //RxJavaTutorial: send event 146
    //RxJavaTutorial: send event 147
    //RxJavaTutorial: send event 148
    //RxJavaTutorial: send event 149
    //RxJavaTutorial: send event 150

    /**
     * Trigger
     */
    //RxJavaTutorial: onNext : 1
    //RxJavaTutorial: onNext : 2
    //RxJavaTutorial: onNext : 3
    //RxJavaTutorial: onNext : 4
    //RxJavaTutorial: onNext : 5
    //RxJavaTutorial: onNext : 6
    //RxJavaTutorial: onNext : 7
    //RxJavaTutorial: onNext : 8
    //RxJavaTutorial: onNext : 9
    //RxJavaTutorial: onNext : 10
    //RxJavaTutorial: onNext : 11
    //RxJavaTutorial: onNext : 12
    //...
    //RxJavaTutorial: onNext : 123
    //RxJavaTutorial: onNext : 124
    //RxJavaTutorial: onNext : 125
    //RxJavaTutorial: onNext : 126
    //RxJavaTutorial: onNext : 127
    //RxJavaTutorial: onNext : 128
    //RxJavaTutorial: onComplete

    /**
     * Trigger again
     *
     * do nothing!
     */
}