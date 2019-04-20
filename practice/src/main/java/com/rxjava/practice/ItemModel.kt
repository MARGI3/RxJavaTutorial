package com.rxjava.practice

import android.support.annotation.StringRes
import com.rxjava.practice.intentcreator.IntentCreator

class ItemModel @JvmOverloads constructor(
    @StringRes title: Int,
    clazz: Class<out ItemRunnable>,
    intentCreator: IntentCreator = NormalIntentCreator()
) {
    var mTitle = title
    var mClazz = clazz
    var mIntentCreator = intentCreator
}