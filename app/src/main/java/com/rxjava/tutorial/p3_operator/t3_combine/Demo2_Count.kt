package com.rxjava.tutorial.p3_operator.t3_combine

import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer

@Suppress("ClassName")
/**
 * author : magic
 * date   : 18/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 统计Observable发送事件的数量
 *
 * Counting the size that the Observable had sent
 */
class Demo2_Count : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        val disposable = Observable.fromArray("A", "B", "C", "D", "E", "F", "G", "H", "I")
                .count()
                .subscribe(object : Consumer<Long> {

                    override fun accept(t: Long?) {
                        Log.d(TAG, "receive event $t times")
                    }

                })
    }

    //RxJavaTutorial: receive event 9 times
}