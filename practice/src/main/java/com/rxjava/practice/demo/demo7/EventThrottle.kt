package com.rxjava.practice.demo.demo7

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

/**
 * {@link com.rxjava.practice.demo.demo7.EventThrottleFragment}
 */
class EventThrottle : ItemRunnable(), Cancelable {

    private var mCompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    @Suppress("ObjectLiteralToLambda")
    override fun run() {
        super.run()
    }

    override fun cancel() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }
}