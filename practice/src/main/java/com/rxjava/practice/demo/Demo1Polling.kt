package com.rxjava.practice.demo

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import com.rxjava.practice.api.TranslateService
import com.rxjava.practice.model.Translation
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Demo1Polling : ItemRunnable(), Cancelable {

    private val mBaseUrl = "http://fy.iciba.com/"

    private var mCompositeDisposable = CompositeDisposable()

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
        Observable.interval(2, TimeUnit.SECONDS)
            .doOnNext(object : Consumer<Long> {

                override fun accept(t: Long?) {

                    Log.d(TAG, "this is No.$t polling request")

                    val retrofit = Retrofit.Builder()
                        .baseUrl(mBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

                    val translateService = retrofit.create(TranslateService::class.java)

                    val observable = translateService.translate()

                    observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Translation> {

                            override fun onSubscribe(d: Disposable) {
                                Log.d(TAG, "request onSubscribe")
                                mCompositeDisposable.add(d)
                            }

                            override fun onNext(t: Translation) {
                                Log.d(TAG, "request onNext ${t.content}")
                            }

                            override fun onComplete() {
                                Log.d(TAG, "request onComplete")
                            }

                            override fun onError(e: Throwable) {
                                Log.e(TAG, "request onError $e")
                            }
                        })


                }
            }).subscribe(object : Observer<Long> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                    mCompositeDisposable.add(d)
                }

                override fun onNext(t: Long) {
                    Log.d(TAG, "onNext $t")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError $e")
                }
            })
    }

    override fun cancel() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    //RxJavaTutorial: onSubscribe
    //RxJavaTutorial: this is No.0 polling request
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: onNext 0
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
    //RxJavaTutorial: this is No.1 polling request
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: onNext 1
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
    //RxJavaTutorial: this is No.2 polling request
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: onNext 2
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
    //RxJavaTutorial: this is No.3 polling request
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: onNext 3
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
    //RxJavaTutorial: this is No.4 polling request
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: onNext 4
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
}