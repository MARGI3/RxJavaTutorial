package com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer

@Suppress("ClassName")
class Demo7_Reduce : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val  observable = Observable.just(1, 2, 3, 4)

        val disposable = observable.reduce(object : BiFunction<Int, Int, Int> {

            override fun apply(t1: Int, t2: Int): Int {
                Log.d(TAG, "combine data : $t1 - $t2")
                return t1 * t2
                //根据返回方法的处理方式，可以是 累乘  累加 或者其他运算
                //规则：
                // (1)   data1 apply data2 = result1
                // (2)   result1 apply data3 = result2
                // (3)   result2 apply data4 = result3
            }

        }).subscribe(object : Consumer<Int> {

            override fun accept(t: Int?) {
                Log.d(TAG, "result : $t")
            }

        })
    }

}