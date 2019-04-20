package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

/**
 * discard items emitted by an Observable until a specified condition becomes false
 *
 * 不发送事件 直到当前 event predicate 结果是 false，才开始发送事件 （当前事件的后续事件也会发送，无论后续事件的 predicate 结果）
 */
class Demo3SkipWhile : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        val observable1 = Observable.just(2, 4, 6, 7, 8, 9, 10)

        observable1.skipWhile(object : Predicate<Int> {

            override fun test(t: Int): Boolean {
                val predicate = t % 2 == 0
                Log.d(TAG, "skipWhile test : $t , predicate : $predicate")
                return predicate
            }
        }).subscribe(object : Consumer<Int> {

            override fun accept(t: Int) {
                Log.d(TAG, "result $t")
            }

        })

        //RxJavaTutorial: skipWhile test : 2 , predicate : true
        //RxJavaTutorial: skipWhile test : 4 , predicate : true
        //RxJavaTutorial: skipWhile test : 6 , predicate : true
        //RxJavaTutorial: skipWhile test : 7 , predicate : false
        //RxJavaTutorial: result 7
        //RxJavaTutorial: result 8
        //RxJavaTutorial: result 9
        //RxJavaTutorial: result 10
    }
}