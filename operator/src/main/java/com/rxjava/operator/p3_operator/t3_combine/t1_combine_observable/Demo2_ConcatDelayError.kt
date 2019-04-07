package com.rxjava.operator.p3_operator.t3_combine.t1_combine_observable

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 应用场景
 *
 * 合并多个 Observable时，其中一个出现异常，需要将异常delay到所有 event 处理完毕之后再出发 onError
 *
 * 如果不通过 concatDelayError 操作，则异常发生，后续 event 直接中断发送，回调 onError
 */
@Suppress("ClassName")
class Demo2_ConcatDelayError : ItemRunnable() {

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val observable1 = Observable.just(1, 2, 3, 4)
        val observable2 = Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(5)
                emitter.onNext(6)
                emitter.onNext(7)
                /** 发送Error事件，因为无使用concatDelayError，所以 observable3 将不会发送事件 */
                emitter.onError(NullPointerException())
                emitter.onComplete()
            }
        })
        val observable3 = Observable.just(8, 9, 10)

        val concatObservable = Observable.concat(observable1, observable2, observable3)

        concatObservable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "receive event $t")
            }

            override fun onComplete() {
                Log.d(TAG, "handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "handle error $e")
            }
        })


        val concatDelayErrorObservable = Observable.concatArrayDelayError(observable1, observable2, observable3)

        concatDelayErrorObservable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "delayError start subscribe")
            }

            override fun onNext(t: Int) {
                Log.d(TAG, "delayError receive event $t")
            }

            override fun onComplete() {
                Log.d(TAG, "delayError handle complete")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "delayError handle error $e")
            }
        })
    }

}