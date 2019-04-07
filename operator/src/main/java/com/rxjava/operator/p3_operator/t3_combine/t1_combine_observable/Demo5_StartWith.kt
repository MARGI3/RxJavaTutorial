package com.rxjava.operator.p3_operator.t3_combine.t1_combine_observable

import android.util.Log
import com.rxjava.operator.ItemRunnable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : magic
 * date   : 18/03/2019
 * mail   : 562224864cross@gmail.com
 */

/**
 * 在 Observable 发送事件之前 追加发送一些事件
 */
@Suppress("ClassName")
class Demo5_StartWith : ItemRunnable() {

    override fun run() {
        super.run()
        Observable.just(4, 5, 6, 7)
                .startWith(3)
                .startWithArray(0, 1, 2).subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "----------start-----------")
                        Log.d(TAG, "start subscribe")
                    }

                    override fun onNext(t: Int) {
                        Log.d(TAG, "receive event $t")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "handle complete")
                        Log.d(TAG, "----------finish-----------")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "handle error $e")
                        Log.d(TAG, "----------finish-----------")
                    }

                })


        Observable.just(4, 5, 6, 7)
                .startWith(Observable.just(2, 3))
                .startWithArray(0, 1).subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "----------start-----------")
                        Log.d(TAG, "start subscribe")
                    }

                    override fun onNext(t: Int) {
                        Log.d(TAG, "receive event $t")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "handle complete")
                        Log.d(TAG, "----------finish-----------")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "handle error $e")
                    }

                })
    }

    /**
     * 执行顺序，越在后面调用startWith，则顺序越靠前 (方法内部是用 concat 实现的)
     */

    //RxJavaTutorial: ----------start-----------
    //RxJavaTutorial: start subscribe
    //RxJavaTutorial: receive event 0
    //RxJavaTutorial: receive event 1
    //RxJavaTutorial: receive event 2
    //RxJavaTutorial: receive event 3
    //RxJavaTutorial: receive event 4
    //RxJavaTutorial: receive event 5
    //RxJavaTutorial: receive event 6
    //RxJavaTutorial: receive event 7
    //RxJavaTutorial: handle complete
    //RxJavaTutorial: ----------finish-----------
    //RxJavaTutorial: ----------start-----------
    //RxJavaTutorial: start subscribe
    //RxJavaTutorial: receive event 0
    //RxJavaTutorial: receive event 1
    //RxJavaTutorial: receive event 2
    //RxJavaTutorial: receive event 3
    //RxJavaTutorial: receive event 4
    //RxJavaTutorial: receive event 5
    //RxJavaTutorial: receive event 6
    //RxJavaTutorial: receive event 7
    //RxJavaTutorial: handle complete
    //RxJavaTutorial: ----------finish-----------

}