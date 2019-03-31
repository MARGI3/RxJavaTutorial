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
 * determine whether an Observable emits a particular item or not
 *
 * 判断Observable要发送的事件中是否包含某个事件
 */
@Suppress("ClassName")
class Demo8_Contains : ItemRunnable() {

    @SuppressLint("CheckResult")
    override fun run() {
        super.run()

        val observable1 = Observable.just(4, 5, 6)

        observable1.contains(6)
            .subscribe(Consumer<Boolean> {
                Log.d(TAG, "contains result $it")
            })
    }
}