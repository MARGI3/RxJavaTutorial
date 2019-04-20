package com.rxjava.operator.p3_operator.t5_back_pressure.async

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("SpellCheckingInspection")
class Demo1FlowableAsync : ItemRunnable() {

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
                 * 异步订阅关系中
                 *
                 * 如果不调用 request() 向上游请求数据
                 *
                 * 1. 则 subscriber 中的 onNext 回调不会触发
                 * 2. 上游仍然会继续发送数据，直到 超出 BufferSize (128) 然后 onError
                 * 3. 下游可以在任意时间 调用 request() 方法向上游请求数据
                 * 4. 只有下游消耗完上游数据时，subscriber 中的 onComplete 才会回调
                 */
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
        flowable.subscribeOn(Schedulers.io())//在异步线程进行事件发送
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

        /**
         * 这里的订阅关系是异步订阅 (事件发送 和 观察 在不同线程)
         *
         * 异步订阅 才存在缓冲区的概念
         *
         * 注意跟 Demo1Flowable log 进行对比
         * 注意跟 Demo1FlowableSync log 进行对比
         */

        //RxJavaTutorial: onSubscribe
        //RxJavaTutorial: sendEvent 1
        //RxJavaTutorial: sendEvent 2
        //RxJavaTutorial: sendEvent 3
        //RxJavaTutorial: sendEvent 4
        //RxJavaTutorial: sendComplete
        //RxJavaTutorial: onNext 1
        //RxJavaTutorial: onNext 2
        //RxJavaTutorial: onNext 3
        //RxJavaTutorial: onNext 4
        //RxJavaTutorial: onComplete
    }

}