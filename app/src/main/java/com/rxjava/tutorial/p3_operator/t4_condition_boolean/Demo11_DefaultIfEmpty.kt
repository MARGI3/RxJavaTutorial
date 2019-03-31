package com.rxjava.tutorial.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.tutorial.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiPredicate
import io.reactivex.functions.Consumer
import io.reactivex.internal.schedulers.IoScheduler
import java.util.concurrent.TimeUnit

/**
 * emit items from the source Observable, or a default item if the source Observable emits nothing
 *
 * 当 observable 没有发送任何 event， 仅发送 onComplete 时，可以指定一个 默认 event value
 * 当 observable 发送事件时，默认值将不会被使用
 */
@Suppress("ClassName")
class Demo11_DefaultIfEmpty : ItemRunnable() {

    @SuppressLint("CheckResult")
    override fun run() {
        super.run()
        Observable.create(ObservableOnSubscribe<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
            it.onComplete()
        }).defaultIfEmpty(-1).subscribe {
            Log.d(TAG, "DefaultIfEmpty default value : $it")
        }

        //RxJavaTutorial: DefaultIfEmpty default value : -1
    }
}