package com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * 当两个Observables中的任何一个发送了数据后
 * 将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合
 * 最终基于该函数的结果发送数据
 */
@Suppress("ClassName")
class Demo6_CombineLatest : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val observable1 = Observable.just(1L, 2L, 3L)

        val observable2 = Observable.intervalRange(0L, 4, 1, 1, TimeUnit.SECONDS)

        @Suppress("UNUSED_VARIABLE")
        val disposable = Observable.combineLatest(observable1, observable2, object : BiFunction<Long, Long, Long> {

            override fun apply(t1: Long, t2: Long): Long {
                // o1 = 第1个Observable发送的最新（最后）1个数据
                // o2 = 第2个Observable发送的每1个数据
                Log.d(TAG, "combine data： $t1 - $t2 ")
                return t1 + t2
            }

        }).subscribe(object : Consumer<Long> {

            override fun accept(t: Long?) {
                Log.d(TAG, "result： $t")

            }

        })

        //Observable.combineLatestDelayError  the same like mergeDelayError concatDelayError
    }

}