package com.rxjava.operator.p3_operator.t5_back_pressure.sync

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("ClassName")
class Demo1_FlowableSync : ItemRunnable() {

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {

            Log.d(TAG, "sendEvent 1")
            it.onNext(1)

            Log.d(TAG, "sendEvent 2")
            it.onNext(2)

            Log.d(TAG, "sendEvent 3")
            it.onNext(3)

            Log.d(TAG, "sendEvent 4")
            it.onNext(4)

            Log.d(TAG, "sendComplete")
            it.onComplete()

        }, BackpressureStrategy.ERROR)

        //创建subscriber
        val subscriber = object : Subscriber<Int> {

            override fun onSubscribe(s: Subscription?) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription

                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接

                // 不同点：Subscription增加了void request(long n)
                Log.d(TAG, "onSubscribe $s")

                /**
                 * 同步订阅关系中
                 *
                 * 如果不调用 request() 向上游请求数据
                 *
                 * 1. 会触发异常 (BackpressureStrategy.ERROR 的前提下)，详见log
                 * 2. 下游消费一个事件之后，上游才能继续发送下一个事件
                 */
//                s?.request(Long.MAX_VALUE)
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

        /**
         * 这里的订阅关系是同步订阅(事件发布 和 观察在相同线程)
         *
         * 同步订阅关系中 没有缓冲区的概念
         *
         * 注意跟 Demo1_Flowable log 进行对比
         * 注意跟 Demo1_FlowableAsync log 进行对比
         */

        //D/RxJavaTutorial: onSubscribe 0
        //D/RxJavaTutorial: sendEvent 1
        //E/RxJavaTutorial: onError io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
        //D/RxJavaTutorial: sendEvent 2
        //D/RxJavaTutorial: sendEvent 3
        //D/RxJavaTutorial: sendEvent 4
        //D/RxJavaTutorial: sendComplete
    }

}