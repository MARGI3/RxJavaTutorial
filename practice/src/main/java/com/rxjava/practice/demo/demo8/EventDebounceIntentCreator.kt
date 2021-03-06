package com.rxjava.practice.demo.demo8

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.rxjava.practice.ItemRunnable
import com.rxjava.practice.intentcreator.FragmentWrapperActivity
import com.rxjava.practice.intentcreator.IntentCreator

class EventDebounceIntentCreator : IntentCreator {

    override fun createIntent(context: Context, clazz: Class<out ItemRunnable>, title: Int): Intent {
        val bundle = Bundle()
        bundle.putSerializable(IntentCreator.KEY_EXTRA_EXECUTOR, clazz)
        bundle.putInt(IntentCreator.KEY_EXTRA_TITLE, title)
        return FragmentWrapperActivity.createIntent(context, EventDebounceFragment::class.java, bundle)
    }

}