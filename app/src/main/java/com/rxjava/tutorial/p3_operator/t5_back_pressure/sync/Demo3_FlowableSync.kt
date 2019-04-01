package com.rxjava.tutorial.p3_operator.t5_back_pressure.sync

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("ClassName")
class Demo3_FlowableSync : ItemRunnable() {

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {

            /**
             * FlowableEmitter.request() 可以获取下游观察者 Subscriber 能接受的事件数量
             *
             * 1. 假设下游观察者 Subscriber 中 Subscription.request(n) 告诉上游 Flowable 需要 n 个事件
             *
             * 2. 那么上游 Flowable 中 FlowableEmitter.requested() 返回值 就是 n
             *
             */
            val subscriberRequestedSize = it.requested()

            for (index in 1..subscriberRequestedSize) {
                Log.d(TAG, "send event $index")
                it.onNext(index.toInt())
            }

            it.onComplete()

        }, BackpressureStrategy.ERROR)

        //创建subscriber
        val subscriber = object : Subscriber<Int> {

            override fun onSubscribe(s: Subscription?) {

                Log.d(TAG, "onSubscribe $s")

                val eventSize = 10L

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

        //RxJavaTutorial: onSubscribe 0
        //RxJavaTutorial: Subscription request 10 event
        //RxJavaTutorial: send event 1
        //RxJavaTutorial: onNext 1
        //RxJavaTutorial: send event 2
        //RxJavaTutorial: onNext 2
        //RxJavaTutorial: send event 3
        //RxJavaTutorial: onNext 3
        //RxJavaTutorial: send event 4
        //RxJavaTutorial: onNext 4
        //RxJavaTutorial: send event 5
        //RxJavaTutorial: onNext 5
        //RxJavaTutorial: send event 6
        //RxJavaTutorial: onNext 6
        //RxJavaTutorial: send event 7
        //RxJavaTutorial: onNext 7
        //RxJavaTutorial: send event 8
        //RxJavaTutorial: onNext 8
        //RxJavaTutorial: send event 9
        //RxJavaTutorial: onNext 9
        //RxJavaTutorial: send event 10
        //RxJavaTutorial: onNext 10
        //RxJavaTutorial: onComplete

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