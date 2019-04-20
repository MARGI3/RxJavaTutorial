package com.rxjava.operator.p3_operator.t5_back_pressure.strategy

import android.util.Log
import android.view.View
import com.rxjava.operator.ITrigger
import com.rxjava.operator.ItemRunnable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

/**
 * 上游发送事件速度 > 下游消耗事件的速度 (流速不匹配)
 *
 * 缓存区已经存满, 上游仍然在继续发送事件
 *
 * 采用何种策略处理以上的问题
 */
@Suppress("SpellCheckingInspection")
class Demo6FlowableInterval : ItemRunnable(), ITrigger {

    private var mSubscription: Subscription? = null

    override fun run() {
        super.run()

        //上游每隔一毫秒发送一个事件

        //下游每隔一秒钟消耗一个事件

        Flowable.interval(1, TimeUnit.MILLISECONDS)
            .onBackpressureBuffer()
            .observeOn(Schedulers.newThread()).subscribe(object : Subscriber<Long> {

                override fun onSubscribe(subscription: Subscription?) {
                    Log.d(TAG, "onSubscribe $subscription")
                    mSubscription = subscription
                    mSubscription?.request(Long.MAX_VALUE)
                }

                override fun onNext(t: Long?) {

                    Log.d(TAG, "onNext : $t")

                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        Log.e(TAG, "$e")
                    }
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError : $t")
                }
            })

        /**
         * onBackpressureBuffer()
         * onBackpressureDrop()
         * onBackpressureLatest()
         *
         * 通过以上的方式来设置 BackpressureStrategy
         */

    }

    override fun onTrigger(view: View?) {
        mSubscription?.cancel()
    }

}