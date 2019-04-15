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

class EventBlockFragment : BaseItemFragment() {

    private lateinit var mButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_event_block, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mButton = view.findViewById(R.id.execute)

        /**
         * 1. 此处采用了RxBinding
         * 2. 传入Button控件，点击时，都会发送数据事件（但由于使用了throttleFirst（）操作符，所以只会发送该段时间内的第1次点击事件）
         **/
        mButton.clicks()
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe(object : Observer<Unit> {

                override fun onSubscribe(d: Disposable) {
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