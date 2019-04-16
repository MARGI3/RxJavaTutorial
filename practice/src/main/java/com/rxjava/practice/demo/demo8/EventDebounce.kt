package com.rxjava.practice.demo.demo8

import android.annotation.SuppressLint
import com.rxjava.practice.Cancelable
import com.rxjava.practice.ItemRunnable
import io.reactivex.disposables.CompositeDisposable

class EventDebounce : ItemRunnable(), Cancelable {

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