package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * 判断observable发送事件是否为空
 */
@Suppress("ClassName")
class Demo9_IsEmpty : ItemRunnable() {

    @SuppressLint("CheckResult")
    override fun run() {
        super.run()

        val observable1 = Observable.just(4, 5, 6)

        observable1.isEmpty
            .subscribe(Consumer<Boolean> {
                Log.d(TAG, "isEmpty result $it")
            })
    }
}