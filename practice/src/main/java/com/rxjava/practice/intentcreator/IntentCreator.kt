package com.rxjava.practice.intentcreator

import android.content.Context
import android.content.Intent
import android.support.annotation.IntegerRes
import com.rxjava.practice.ItemRunnable

interface IntentCreator {

    companion object {
        const val KEY_EXTRA_EXECUTOR = "key.extra.class"
        const val KEY_EXTRA_TITLE = "key.extra.title"
    }

    fun createIntent(context: Context, clazz: Class<out ItemRunnable>, @IntegerRes title: Int): Intent

}