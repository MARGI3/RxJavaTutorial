package com.rxjava.practice.demo.demo7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.jakewharton.rxbinding3.view.clicks
import com.rxjava.practice.R
import com.rxjava.practice.intentcreator.BaseItemFragment
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

internal class EventThrottleFragment : BaseItemFragment() {

    private lateinit var mButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_event_throttle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mButton = view.findViewById(R.id.execute)

        /**
         * 1. RxBinding
         * 2. Button click will send event
         **/
        mButton.clicks()
            .throttleFirst(2, TimeUnit.SECONDS) //second click event will send after 2s first event be sent
            .subscribe(object : Observer<Unit> {

                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(t: Unit) {
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

}