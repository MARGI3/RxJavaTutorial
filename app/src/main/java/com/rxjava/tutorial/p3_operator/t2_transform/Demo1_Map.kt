package com.rxjava.tutorial.p3_operator.t2_transform

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

/**
 * author : magic
 * date   : 03/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 对 Observable 发送的每一个事件 通过指定函数 处理 转换成 "另外一种"(新的对象，可以是同一个类型) 事件
 *
 * 将 Observable 发送的事件转换为任意的类型事件
 */
class Demo1_Map : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val observable = Observable.create(object : ObservableOnSubscribe<Int> {

            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
            }

        }).map(object : Function<Int, String> {

            //onNext() 每调用一次 都会对应触发一次 map 操作
            override fun apply(t: Int): String {
                val result = "No - $t"
                Log.d(TAG, "map  input $t, output $result")
                return result
            }

        }).subscribe(object : Consumer<String> {

            override fun accept(t: String?) {
                Log.d(TAG, "onNext $t")
            }

        })

        //RxJavaTutorial: map  input 1, output No - 1
        //RxJavaTutorial: onNext No - 1
        //RxJavaTutorial: map  input 2, output No - 2
        //RxJavaTutorial: onNext No - 2
        //RxJavaTutorial: map  input 3, output No - 3
        //RxJavaTutorial: onNext No - 3

    }
}