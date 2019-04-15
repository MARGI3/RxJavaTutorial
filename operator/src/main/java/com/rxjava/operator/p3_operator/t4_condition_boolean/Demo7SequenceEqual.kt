package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.BiPredicate
import io.reactivex.functions.Consumer

/**
 * 判断两个Observable 发送的数据是否相同 （数据相同）
 *
 * 也可以自定义 "相同" 的条件
 */
class Demo7SequenceEqual : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    @SuppressLint("CheckResult")
    override fun run() {
        super.run()

        val observable1 = Observable.just(4, 5, 6)

        val observable2 = Observable.just(4, 5, 6)

        Observable.sequenceEqual(observable1, observable2).subscribe(object : Consumer<Boolean> {

            override fun accept(t: Boolean?) {
                Log.d(TAG, "result1 $t")
            }
        })

        val observable3 = Observable.just(8, 10, 12)

        Observable.sequenceEqual(observable1, observable3, object : BiPredicate<Int, Int> {

            override fun test(t1: Int, t2: Int): Boolean {
                val predicate = t2 % t1 == 0
                Log.d(TAG, "t1 $t1, t2 $t2, equal predicate $predicate")
                return predicate
            }

        }).subscribe(object : Consumer<Boolean> {

            override fun accept(t: Boolean?) {
                Log.d(TAG, "result2 $t")
            }
        })

        //RxJavaTutorial: result1 true
        //RxJavaTutorial: t1 4, t2 8, equal predicate true
        //RxJavaTutorial: t1 5, t2 10, equal predicate true
        //RxJavaTutorial: t1 6, t2 12, equal predicate true
        //RxJavaTutorial: result2 true
    }
}