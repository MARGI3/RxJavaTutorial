package com.rxjava.tutorial.p3_operator.t5_back_pressure

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("ClassName")
class Demo1_Flowable : ItemRunnable() {

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

                s?.request(Long.MAX_VALUE)
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
         * 这里的订阅关系是同步订阅
         */

        //RxJavaTutorial: onSubscribe 0
        //RxJavaTutorial: sendEvent 1
        //RxJavaTutorial: onNext 1
        //RxJavaTutorial: sendEvent 2
        //RxJavaTutorial: onNext 2
        //RxJavaTutorial: sendEvent 3
        //RxJavaTutorial: onNext 3
        //RxJavaTutorial: sendEvent 4
        //RxJavaTutorial: onNext 4
        //RxJavaTutorial: sendComplete
        //RxJavaTutorial: onComplete
    }

}