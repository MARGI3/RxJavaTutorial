package com.rxjava.operator

import android.support.annotation.StringRes

class ItemModel(@StringRes title: Int, clazz: Class<out ItemRunnable>) {
    var mTitle = title
    var mClazz = clazz
}