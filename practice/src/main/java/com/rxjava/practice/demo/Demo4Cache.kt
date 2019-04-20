package com.rxjava.practice.demo

import android.annotation.SuppressLint
import android.util.Log
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class Demo4Cache : ItemRunnable(), Cancelable {

    private var mCompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val memoryCache: String? = null
        var diskCache: String?

        //从内存获取
        val memoryObservable = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(emitter: ObservableEmitter<String>) {

                Log.d(TAG, "memoryObservable subscribe")
                if (memoryCache != null) {
                    emitter.onNext(memoryCache)
                } else {
                    emitter.onComplete()
                }
            }
        })

        //从存储获取
        val diskObservable = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(emitter: ObservableEmitter<String>) {

                Log.d(TAG, "diskObservable subscribe")

                diskCache = "disk cache"

                if (diskCache != null) {
                    Log.d(TAG, "diskObservable onNext")
                    emitter.onNext(diskCache!!)
                } else {
                    emitter.onComplete()
                }
            }
        })

        //从网络请求中获取结果
        val networkObservable = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(emitter: ObservableEmitter<String>) {
                Log.d(TAG, "networkObservable subscribe")
                //network request
            }
        }).subscribeOn(Schedulers.io())

        //concat 连接三个 observable之后，三个observable会 依次执行
        //符合 内存 -> 硬盘 -> 网络  的缓存策略
        //firstElement() 取三个事件中的 第一个 event, 取到第一个 event 之后, 上游的 Observable 事件将不会继续发送
        Observable.concat(memoryObservable, diskObservable, networkObservable)
            .firstElement()
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                   Log.d(TAG, "get source from $t")
                }
            })

    }

    override fun cancel() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

}