package com.rxjava.practice.p1_polling

import android.annotation.SuppressLint
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import com.rxjava.practice.api.TranslateService
import com.rxjava.practice.model.Translation
import com.rxjava.practice.model.Translation2
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("ClassName")
class Demo3_NestingRequest : ItemRunnable(), Cancelable {

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

        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(object : Consumer<Translation> {

                    override fun accept(result: Translation?) {
                        Log.d(TAG, "observable1 request success, current thread ${Thread.currentThread()}")
                        Log.d(TAG, "content = ${result?.content}")
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(object : Function<Translation, ObservableSource<Translation2>> {

                    override fun apply(result: Translation): ObservableSource<Translation2> {
                        /**
                         * 这里可以根据 上一个请求的结果 Translation 来决定 observable2 是否发送
                         */
                        return if (result.status == 1) {
                            observable2
                        } else {
                            Observable.error(Throwable("request 1 failed, stop request 2"))
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Translation2> {

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "onSubscribe")
                        mCompositeDisposable.add(d)
                    }

                    override fun onNext(result2: Translation2) {
                        Log.d(TAG, "observable2 request success, current thread ${Thread.currentThread()}")
                        Log.d(TAG, "content2 = ${result2.content}")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "failed : ${e.message}")
                    }

                })

    }

    override fun cancel() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    //RxJavaTutorial: onSubscribe
    //RxJavaTutorial: observable1 request success, current thread Thread[main,5,main]
    //RxJavaTutorial: content = Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: observable2 request success, current thread Thread[main,5,main]
    //RxJavaTutorial: content2 = Content(from=en-EU, to=zh-CN, vendor=tencent, out=第二喜世界, errorNum=null)
    //RxJavaTutorial: onComplete
}