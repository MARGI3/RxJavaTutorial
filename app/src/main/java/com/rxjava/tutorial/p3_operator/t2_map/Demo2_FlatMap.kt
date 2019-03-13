package com.rxjava.tutorial.p3_operator.t2_map

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