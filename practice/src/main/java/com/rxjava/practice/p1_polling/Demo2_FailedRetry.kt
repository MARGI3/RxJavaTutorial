package com.rxjava.practice.p1_polling

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import com.rxjava.practice.api.TranslateService
import com.rxjava.practice.model.Translation
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
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val MAX_RETRY_COUNT = 6
private const val RETRY_INTERVAL = 1L

@Suppress("ClassName")
class Demo2_FailedRetry : ItemRunnable(), Cancelable {

    private val mBaseUrl = "http://fy.iciba.com/"

    private var mCompositeDisposable = CompositeDisposable()

    private var mRetryInterval = 0L

    private var mCurrentRetryCount = 0

    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()

        val retrofit = Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val translateService = retrofit.create(TranslateService::class.java)

        val observable = translateService.translate()

        observable.retryWhen(object : Function<Observable<Throwable>, ObservableSource<*>> {

            override fun apply(throwableObservable: Observable<Throwable>): ObservableSource<*> {

                /**
                 * 用flatMap 将请求 Observable 转换成 另一种 ObservableSource
                 */
                return throwableObservable.flatMap(object : Function<Throwable, ObservableSource<*>> {

                    override fun apply(t: Throwable): ObservableSource<*> {

                        Log.d(TAG,  "request throwable = $t ")

                        /**
                         * 根据请求Observable中的 Throwable 来判断是否要重试
                         */
                        if (t is IOException) {

                            //IOException need retry

                            //判断重试次数
                            return if (mCurrentRetryCount < MAX_RETRY_COUNT) {

                                mCurrentRetryCount++

                                Log.d(TAG, "this is No.$mCurrentRetryCount retry")

                                mRetryInterval = mCurrentRetryCount * RETRY_INTERVAL

                                //依次增加重试事件
                                Observable.just(1).delay(mRetryInterval, TimeUnit.SECONDS)
                            } else {

                                Observable.error<Throwable>(Throwable("failed with $mCurrentRetryCount times retry"))
                            }

                        } else {
                            return Observable.error<Throwable>(Throwable("request exception without retry!"))
                        }
                    }
                })
            }
        })
            .subscribeOn(Schedulers.io())
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

    override fun cancel() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    /**
     * disconnect the network, retry times out
     */
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.1 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.2 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.3 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.4 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.5 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.6 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: request onError java.lang.Throwable: failed with 6 times retry

    /**
     * disconnect then connect the network
     */
    //RxJavaTutorial: request onSubscribe
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.1 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.2 retry
    //RxJavaTutorial: request throwable = java.net.UnknownHostException: Unable to resolve host "fy.iciba.com": No address associated with hostname
    //RxJavaTutorial: this is No.3 retry
    //RxJavaTutorial: request onNext Content(from=en-EU, to=zh-CN, vendor=tencent, out=嗨世界, errorNum=null)
    //RxJavaTutorial: request onComplete
}