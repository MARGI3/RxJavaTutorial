package com.rxjava.practice

import android.support.annotation.StringRes

class ItemModel(@StringRes title: Int, clazz: Class<out ItemRunnable>) {
    var mTitle = title
    var mClazz = clazz
}