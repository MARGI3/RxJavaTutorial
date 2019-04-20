package com.rxjava.operator.p3_operator.t4_condition_boolean

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * given two or more source Observables, emit all of the items from only the first of these Observables to emit an item (Event / onComplete / onError)
 *
 * 多个 observable, 最先(时间先后顺序)发送事件(Event / onComplete / onError)的 observable 可以发送完自己的event, 其他 observable 的事件将不会发送
 *
 * 即: 谁先(时间先后顺序)发送事件，谁就具有事件的发送权利
 */
class Demo10Amb : ItemRunnable() {

    @SuppressLint("CheckResult")
    override fun run() {
        super.run()

        //delay 4s send event
        val observable1 = Observable.timer(4, TimeUnit.SECONDS)

        //delay 3s send event
        val observable2 = Observable.just("A", "B", "C", "D").delay(3, TimeUnit.SECONDS)

        //delay 4s send event
        val observable3 = Observable.interval(10, 4, TimeUnit.SECONDS)

        val observableList = arrayListOf(observable1, observable2, observable3)

        Observable.amb(observableList)
            .subscribe(object : Observer<Any> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe , current time : ${System.currentTimeMillis()}")
                }

                override fun onNext(t: Any) {
                    Log.d(TAG, "onNext $t , current time : ${System.currentTimeMillis()}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete , current time : ${System.currentTimeMillis()}")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError $e")
                }
            })

        //RxJavaTutorial: onSubscribe , current time : 1553657598180
        //RxJavaTutorial: onNext A , current time : 1553657601183
        //RxJavaTutorial: onNext B , current time : 1553657601183
        //RxJavaTutorial: onNext C , current time : 1553657601183
        //RxJavaTutorial: onNext D , current time : 1553657601183
        //RxJavaTutorial: onComplete , current time : 1553657601183
    }
}