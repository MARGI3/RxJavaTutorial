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
class Demo3_FlowableAsync : ItemRunnable() {

    @Suppress("SpellCheckingInspection")
    override fun run() {
        super.run()

        //创建flowable
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {


            Log.d(TAG, "FlowableEmitter.requested() == ${it.requested()}")

            var flag: Boolean

            for (i in 0..500) {

                flag = false

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

            override fun onSubscribe(s: Subscription?) {

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

    }

}