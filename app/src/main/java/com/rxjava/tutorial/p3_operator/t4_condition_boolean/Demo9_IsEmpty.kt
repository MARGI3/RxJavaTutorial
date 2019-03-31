package com.rxjava.tutorial.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiPredicate
import io.reactivex.functions.Consumer
import io.reactivex.internal.schedulers.IoScheduler
import java.util.concurrent.TimeUnit

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