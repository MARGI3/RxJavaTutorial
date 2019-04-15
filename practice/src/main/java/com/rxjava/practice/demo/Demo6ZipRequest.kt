package com.rxjava.practice.demo

import android.annotation.SuppressLint
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import com.rxjava.practice.api.TranslateService
import com.rxjava.practice.model.Translation
import com.rxjava.practice.model.Translation2
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Demo6ZipRequest : ItemRunnable(), Cancelable {

    private val mBaseUrl = "http://fy.iciba.com/"

    private var mCompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val translateService = retrofit.create(TranslateService::class.java)

        val observable1 = translateService.translate()
        val observable2 = translateService.translate2()

        Observable.zip(observable1, observable2, object : BiFunction<Translation, Translation2, String> {

            override fun apply(t1: Translation, t2: Translation2): String {
                val combineResult = t1.content.toString() + t2.content.toString()
                Log.d(TAG, "zip result $combineResult")
                return combineResult
            }

        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                    mCompositeDisposable.add(d)
                }

                override fun onNext(t: String) {
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
    //RxJavaTutorial: zip result Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)Content(from=en-EU, to=zh-CN, vendor=tencent, out=第二喜世界,
    //RxJavaTutorial: onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)Content(from=en-EU, to=zh-CN, vendor=tencent, out=第二喜世界, erro
    //RxJavaTutorial: onComplete
}