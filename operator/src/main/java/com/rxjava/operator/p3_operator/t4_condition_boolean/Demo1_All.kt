package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

/**
 * determine whether all items emitted by an Observable meet some criteria
 *
 * 判断是否所有发送的event都满足自定义的判断条件
 */
@Suppress("ClassName")
class Demo1_All : ItemRunnable() {

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val observable1 = Observable.just(1, 2, 2, 3, 5, 6, 7)

        observable1.all(object : Predicate<Int> {

            override fun test(t: Int): Boolean {
                val predicate = t < 10
                Log.d(TAG, "all test1 : $t , predicate1 : $predicate")
                return predicate
            }

        }).subscribe(object : Consumer<Boolean> {

            override fun accept(t: Boolean?) {
                Log.d(TAG, "result1 $t")
            }

        })


        val observable2 = Observable.just(2, 4, 6, 7, 8, 10)

        observable2.all(object : Predicate<Int> {

            override fun test(t: Int): Boolean {
                val predicate = t % 2 == 0
                Log.d(TAG, "all test2 : $t , predicate2 : $predicate")
                return predicate
            }

        }).subscribe(object : Consumer<Boolean> {

            override fun accept(t: Boolean?) {
                Log.d(TAG, "result2 $t")
            }

        })

        /**
         * current event test() return false, the events after current will not emit
         *
         * just like && operator
         *
         * 当前 event 返回false，则后续事件将不会发送
         *
         * 类似于 短路 && 判断
         */

        //RxJavaTutorial: all test1 : 1 , predicate1 : true
        //RxJavaTutorial: all test1 : 2 , predicate1 : true
        //RxJavaTutorial: all test1 : 2 , predicate1 : true
        //RxJavaTutorial: all test1 : 3 , predicate1 : true
        //RxJavaTutorial: all test1 : 5 , predicate1 : true
        //RxJavaTutorial: all test1 : 6 , predicate1 : true
        //RxJavaTutorial: all test1 : 7 , predicate1 : true
        //RxJavaTutorial: result1 true
        //RxJavaTutorial: all test2 : 2 , predicate2 : true
        //RxJavaTutorial: all test2 : 4 , predicate2 : true
        //RxJavaTutorial: all test2 : 6 , predicate2 : true
        //RxJavaTutorial: all test2 : 7 , predicate2 : false
        //RxJavaTutorial: result2 false
    }

}