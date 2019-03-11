package com.rxjava.tutorial.p3_map_function

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */
class FlatMapDemo : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val observable = Observable.create(object : ObservableOnSubscribe<Int> {

            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
            }

        }).flatMap(object : Function<Int, ObservableSource<String>> {

            override fun apply(t: Int): ObservableSource<String> {
                val list = arrayListOf<String>()
                val size = 3
                for (i in 0..size) {
                    Log.d(TAG, "add $i")
                    list.add("flat map event $t")
                }

                //模拟 FlatMap 不能保证发送顺序的场景
                val delayTime = Random(2).nextLong(30)
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS)
            }

        }).subscribe(object : Consumer<String> {

            /**
             * 注意Android Logd 机制的 chatty 问题，避免log 数据不对
             */
            override fun accept(t: String?) {
                Log.d(TAG, "onNext accept : $t")
            }

        })

        /**
         * Map vs FlatMap
         *
         * 1.
         * Map 1:1 的事件转换
         * FlatMap 1:1 的事件转换 1:N 的事件转换, N:N 的事件转换
         *         （并且FlatMap不保证事件的顺序）
         *
         * 2.
         * Map Function<Input, Output>
         * FlatMap Function<Input, ObservableSource<Output>>
         * Map 转换数据类型, 返回输出类型
         * FlatMap 转换之后返回的是  ObservableSource<Output>, ObservableSource 还可以支持其他后续的操作
         */

        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
    }

}