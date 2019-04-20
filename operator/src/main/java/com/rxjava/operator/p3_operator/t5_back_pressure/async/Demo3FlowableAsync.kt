package com.rxjava.operator.p3_operator.t5_back_pressure.async

import android.util.Log
import android.view.View
import com.rxjava.operator.ITrigger
import com.rxjava.operator.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("SpellCheckingInspection")
class Demo3FlowableAsync : ItemRunnable(), ITrigger {

    private var mSubscription: Subscription? = null

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {


            Log.d(TAG, "FlowableEmitter.requested() == ${it.requested()}")

            var flag: Boolean

            for (i in 1..500) {

                flag = false

                //dead loop, until requested() size not zero
                while (it.requested() == 0L) {

                    if (!flag) {
                        Log.d(TAG, "stop send event")
                        flag = true
                    }
                }

                //it.requested() != 0 才会发送事件
                Log.d(TAG, "send event $i , current requested() = ${it.requested()}")
                it.onNext(i)
            }

            it.onComplete()

        }, BackpressureStrategy.ERROR)

        //创建subscriber
        val subscriber = object : Subscriber<Int> {

            override fun onSubscribe(subscription: Subscription?) {

                Log.d(TAG, "onSubscribe $subscription")

                mSubscription = subscription

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
        flowable.subscribeOn(Schedulers.io())//在异步线程进行事件发送
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

        /**
         * 这里的订阅关系是异步订阅 (事件发送 和 观察 在不同线程)
         */

    }

    override fun onTrigger(view: View?) {

        mSubscription?.request(48)

    }

    /**
     * 异步订阅关系中
     *
     * * 下游观察者  没办法通过 Subscription.request(n) 来告知上游当前可以消耗的事件
     *   也就是说 上游被观察者 没办法通过 FlowableEmitter.requested() 获取下游设置的 请求值
     *
     * * 建立订阅关系的时候 无论 下游 Subscription.request(n) 设置多少，上游 FlowableEmitter.requested() 获取的值始终是 128 （Flowable内部机制）
     *
     * 1. 上游发送事件，直到缓冲区(size 128) 已经满了, 上游手动控制停止事件发送;  此时 下游并没有 触发 onNext() 所有事件全都在缓冲区
     *
     * 2. Trigger触发，下游 调用 mSubscription?.request(n) 这时，会从缓冲区 消耗 n 个事件
     *
     *    Trigger多次触发
     *
     *          (1)多次从缓冲区获取事件, 如果 n(1) n(2) n(3) ... n(n) 多次冲缓冲区中 **累积** 消耗了 size >= 96 的事件
     *
     *          (2)缓冲区中剩余的 事件 size <= 32
     *
     *          (1) or (2) 满足条件即可 触发 第 3  步骤
     *
     * 3. 这时 机制会自动触发  request(96) { 96为固定值, 并不是为了填满缓冲区 } 告知上游可以继续发送事件 ( FlowableEmitter.requested() == 96)
     *
     * 4. 上游死循环监听 FlowableEmitter.requested() value, 此时 监听到 value != 0 所以上游继续发送事件
     *
     * 5. 以上步骤依次循环，直到 事件发送完
     */

    /**
     * 异步订阅情况
     *
     *
     * 缓冲区 (size = 128) 开始起作用， 维护一个 消耗者 生产者 模式的 均衡
     *
     * 缓冲区的 几个 Trigger size, 下游累积接收 事件 size >= 96 or 缓冲区 剩余事件 size <= 32  都会触发上游继续 发送事件 并且此时的触发值是 96 固定值
     */

}