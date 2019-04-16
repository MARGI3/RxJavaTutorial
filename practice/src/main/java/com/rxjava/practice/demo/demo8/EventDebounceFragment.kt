package com.rxjava.practice.demo.demo8

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jakewharton.rxbinding3.widget.textChanges
import com.rxjava.practice.R
import com.rxjava.practice.api.TranslateService
import com.rxjava.practice.intentcreator.BaseItemFragment
import com.rxjava.practice.model.Translation
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class EventDebounceFragment : BaseItemFragment() {

    private lateinit var mSearchInput: EditText
    private lateinit var mResultTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_event_debounce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSearchInput = view.findViewById(R.id.search)
        mResultTextView = view.findViewById(R.id.text)

        val retrofit = Retrofit.Builder()
            .baseUrl(TranslateService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val translateService = retrofit.create(TranslateService::class.java)

        /**
         * 1. RxBinding extension method
         * 2. EditText text change will send event
         **/
        mSearchInput.textChanges()
            .skipInitialValue() // skip event , when edit text get focus
            .subscribeOn(AndroidSchedulers.mainThread())
            .filter(object : Predicate<CharSequence> {
                override fun test(input: CharSequence): Boolean {

                    val result = input.length > 2

                    if (result) {
                        mResultTextView.text = ""
                    } else {
                        Log.d(TAG, " $input is too short, filtered ")
                    }

                    //key word short than 2, will be blocked
                    return result
                }
            })
            .debounce(1000, TimeUnit.MILLISECONDS) //block too frequency text change, after user finish edit, then send event
            .doOnNext { search -> Log.d(TAG, "key word $search send! ") }
            .observeOn(Schedulers.io())  //network request should be send in Non-UI thread
            .flatMap(object : Function<CharSequence, Observable<Translation>> {

                override fun apply(input: CharSequence): Observable<Translation> {
                    return translateService.translate3(input.toString())
                }
            }).observeOn(AndroidSchedulers.mainThread()) // text view set text should be UI thread
            .subscribe(object : Observer<Translation> {

                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(t: Translation) {
                    mResultTextView.text = t.content.toString()
                    Log.d(TAG, "onNext : ${t.content}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError : $e")
                }
            })
    }
}