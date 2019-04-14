package com.rxjava.practice.api

import com.rxjava.practice.model.Translation
import com.rxjava.practice.model.Translation2
import io.reactivex.Observable
import retrofit2.http.GET

interface TranslateService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun translate(): Observable<Translation>

    @GET("ajax.php?a=fy&f=auto&t=auto&w=second%20hi%20world")
    fun translate2(): Observable<Translation2>
}