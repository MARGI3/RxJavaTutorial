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

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */
class FlatMapDemo : ItemRunnable() {

    companion object {
        private const val TAG = "FlatMapDemo"
    }

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

            override fun accept(t: String?) {
                Log.d(TAG, "onNext accept : $t")
            }

        })

        /**
         * Map 1:1 的事件转换
         * FlatMap 1:N 的事件转换, N;N 的事件转换
         *         （并且FlatMap不保证事件的顺序）
         */
    }

}