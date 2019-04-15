package com.rxjava.operator.p3_operator.t3_combine.t2_combine_event

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer
import java.util.concurrent.Callable

/**
 * 将被观察者Observable发送的数据事件收集到一个数据结构里
 */
class Demo4Collect : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val  observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        @Suppress("UNUSED_VARIABLE")
        val disposable = observable.collect(object : Callable<ArrayList<Int>> {

            override fun call(): ArrayList<Int> {
                // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                return arrayListOf()
            }

        }, object : BiConsumer<ArrayList<Int>, Int> {

            // 2. 对发送的数据进行收集
            override fun accept(list: ArrayList<Int>?, t2: Int?) {

                // 参数说明：list = 容器，t2 = 后者数据
                if (t2!! % 2 == 0) {
                    list!!.add(t2)
                }
            }

        }).subscribe(object : Consumer<List<Int>> {

            override fun accept(t: List<Int>?) {
                Log.d(TAG, "result : $t")
            }

        })
    }

    //RxJavaTutorial: result : [2, 4, 6, 8, 10]

}