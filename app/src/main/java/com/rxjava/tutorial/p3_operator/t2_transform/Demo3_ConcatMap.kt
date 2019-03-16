package com.rxjava.tutorial.p3_operator.t2_transform

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 *
 * 作用：类似FlatMap（）操作符
 *
 * 与FlatMap（）的 区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
 *
 * 有序的将被观察者发送的整个事件序列进行变换
 */

class Demo3_ConcatMap : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val observable = Observable.create(object : ObservableOnSubscribe<Int> {

            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
            }

        }).concatMap(object : Function<Int, ObservableSource<String>> {

            override fun apply(t: Int): ObservableSource<String> {
                val list = arrayListOf<String>()
                val size = 3
                for (i in 0..size) {
                    Log.d(TAG, "add $i")
                    list.add("flat map event $t")
                }

                //模拟 FlatMap 不能保证发送顺序的场景
                val delayTime = Random(2).nextLong()
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

        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: onNext accept : flat map event 1
        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: onNext accept : flat map event 2
        //RxJavaTutorial: add 0
        //RxJavaTutorial: add 1
        //RxJavaTutorial: add 2
        //RxJavaTutorial: add 3
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
        //RxJavaTutorial: onNext accept : flat map event 3
    }

}