package com.rxjava.tutorial.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

/**
 * 当只有一个 observable 的时候
 *
 * 先发送事件，当 predicate 返回 true 则停止后续事件发送
 */
@Suppress("ClassName")
class Demo4_TakeUntil : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        val observable1 = Observable.just(1, 2, 3, 4, 5, 6, 7, 8)

        observable1.takeUntil(object : Predicate<Int> {

            override fun test(t: Int): Boolean {
                val predicate = t > 5
                Log.d(TAG, "takeUntil test : $t , predicate : $predicate")
                return predicate
            }
        }).subscribe(object : Consumer<Int> {

            override fun accept(t: Int) {
                Log.d(TAG, "result $t")
            }

        })

        //RxJavaTutorial: result 1
        //RxJavaTutorial: takeUntil test : 1 , predicate : false
        //RxJavaTutorial: result 2
        //RxJavaTutorial: takeUntil test : 2 , predicate : false
        //RxJavaTutorial: result 3
        //RxJavaTutorial: takeUntil test : 3 , predicate : false
        //RxJavaTutorial: result 4
        //RxJavaTutorial: takeUntil test : 4 , predicate : false
        //RxJavaTutorial: result 5
        //RxJavaTutorial: takeUntil test : 5 , predicate : false
        //RxJavaTutorial: result 6
        //RxJavaTutorial: takeUntil test : 6 , predicate : true
    }
}