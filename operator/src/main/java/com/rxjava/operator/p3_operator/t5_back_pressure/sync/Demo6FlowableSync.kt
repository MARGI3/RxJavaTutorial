package com.rxjava.operator.p3_operator.t5_back_pressure.sync

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("SpellCheckingInspection")
class Demo6FlowableSync : ItemRunnable() {

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {

            /**
             * 3. 异常
             *    当FlowableEmitter.requested() == 0 表明观察者 已经不可接受事件
             *    如果上游继续发送事件 则会抛出异常 MissingBackPressureException
             *
             *    Subscriber 中的 Subscription 未调用 request(n) 请求事件 那么默认值就是 0
             *
             */

            Log.d(TAG, "send event 1, current requested() size = ${it.requested()}")
            it.onNext(1)

            Log.d(TAG, "send event 2, current requested() size = ${it.requested()}")
            it.onNext(2)

            it.onComplete()

        }, BackpressureStrategy.ERROR)

        //创建subscriber
        val subscriber = object : Subscriber<Int> {

            override fun onSubscribe(s: Subscription?) {

                Log.d(TAG, "onSubscribe $s")

                val eventSize = 1L

                Log.d(TAG, "Subscription request $eventSize event")

                s?.request(eventSize)
                //向上游请求数据指定大小的数据
            }

            override fun onNext(t: Int?) {
                Log.d(TAG, "onNext $t")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }

            override fun onError(t: Throwable?) {
                Log.e(TAG, "onError $t")
            }
        }

        //建立订阅关系
        flowable.subscribe(subscriber)

        //D/RxJavaTutorial: onSubscribe 0
        //D/RxJavaTutorial: Subscription request 1 event
        //D/RxJavaTutorial: send event 1, current requested() size = 1
        //D/RxJavaTutorial: onNext 1
        //D/RxJavaTutorial: send event 2, current requested() size = 0
        //E/RxJavaTutorial: onError io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests

        /**
         * 1. 可叠加性
         *    Subscriber 中的 Subscription 可以多次调用 request(n) 请求事件，最终 Flowable 会累加结果
         *    Flowable 中的 FlowableEmitter requested() 返回值 是最终的累加值
         *
         * 2. 实时更新性
         *    Flowable 中的 FlowableEmitter 每次发送事件之后 都会更新 requested() 函数的返回结果
         *    (实时更新观察者能接受的事件)
         *
         * 3. 异常
         *    当FlowableEmitter.requested() == 0 表明观察者 已经不可接受事件
         *    如果上游继续发送事件 则会抛出异常 MissingBackPressureException
         *
         *    Subscriber 中的 Subscription 未调用 request(n) 请求事件 那么默认值就是 0
         */
    }

}