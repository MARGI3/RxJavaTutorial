package com.rxjava.operator.p3_operator.t2_transform

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
 *
 * 原理：
 * 为事件序列中每个事件都创建一个 Observable 对象；
 * 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
 * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
 * 新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
 *
 *
 * 应用场景
 *
 * 无序的将被观察者发送的整个事件序列进行变换
 *
 */
@Suppress("ClassName")
class Demo2_FlatMap : ItemRunnable() {

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

                return Observable.fromIterable(list)
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