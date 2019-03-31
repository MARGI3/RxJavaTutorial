package com.rxjava.tutorial.p3_operator.t5_back_pressure.async

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
class Demo2_FlowableAsync : ItemRunnable() {

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {

            /**
             * 发送超出缓存区size的事件数量
             */
            for (i in 1..129) {
                Log.d(TAG, "send event $i")
                it.onNext(i)
            }
            it.onComplete()

        }, BackpressureStrategy.ERROR)

        //创建subscriber
        val subscriber = object : Subscriber<Int> {

            override fun onSubscribe(s: Subscription?) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription

                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接

                // 不同点：Subscription增加了void request(long n)
                Log.d(TAG, "onSubscribe $s")


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
        flowable.subscribeOn(Schedulers.io())//在异步线程进行事件发送
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

        /**
         * 这里的订阅关系是异步订阅 (事件发送 和 观察 在不同线程)
         */

        //D/RxJavaTutorial: send event 1
        //D/RxJavaTutorial: send event 2
        //D/RxJavaTutorial: send event 3
        // ......
        //D/RxJavaTutorial: send event 128
        //D/RxJavaTutorial: send event 129
        //E/RxJavaTutorial: onError io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
    }

}