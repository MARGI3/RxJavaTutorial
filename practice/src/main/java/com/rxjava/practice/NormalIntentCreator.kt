package com.rxjava.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.rxjava.practice.intentcreator.FragmentWrapperActivity
import com.rxjava.practice.intentcreator.IntentCreator
import com.rxjava.practice.intentcreator.IntentCreator.Companion.KEY_EXTRA_EXECUTOR
import com.rxjava.practice.intentcreator.IntentCreator.Companion.KEY_EXTRA_TITLE

class NormalIntentCreator : IntentCreator {

    override fun createIntent(context: Context, clazz: Class<out ItemRunnable>, title: Int): Intent {
        val bundle = Bundle()
        bundle.putSerializable(KEY_EXTRA_EXECUTOR, clazz)
        bundle.putInt(KEY_EXTRA_TITLE, title)
        return FragmentWrapperActivity.createIntent(context, NormalItemFragment::class.java, bundle)
    }

}