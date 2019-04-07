package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

/**
 * The TakeWhile mirrors the source Observable
 *
 * until such time as some condition you specify becomes false,
 *
 * at which point TakeWhile stops mirroring the source Observable and terminates its own Observable
 *
 * 镜像发送（加上takeWhile之后事件发送与普通发送没有区别）Observable中的事件
 *
 * 当其中一个event不满足条件之后，终止后续事件的发送
 *
 *
 */
@Suppress("ClassName")
class Demo2_TakeWhile : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        val observable1 = Observable.just(2, 4, 6, 7, 8, 10)

        observable1.takeWhile(object : Predicate<Int> {

            override fun test(t: Int): Boolean {
                val predicate = t % 2 == 0
                Log.d(TAG, "takeWhile test : $t , predicate : $predicate")
                return predicate
            }
        }).subscribe(object : Consumer<Int> {

            override fun accept(t: Int) {
                Log.d(TAG, "result $t")
            }

        })

        //RxJavaTutorial: takeWhile test : 2 , predicate : true
        //RxJavaTutorial: result 2
        //RxJavaTutorial: takeWhile test : 4 , predicate : true
        //RxJavaTutorial: result 4
        //RxJavaTutorial: takeWhile test : 6 , predicate : true
        //RxJavaTutorial: result 6
        //RxJavaTutorial: takeWhile test : 7 , predicate : false
    }
}