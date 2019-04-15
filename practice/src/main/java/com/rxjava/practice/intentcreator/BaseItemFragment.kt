package com.rxjava.practice.intentcreator

import android.os.Bundle
import android.support.v4.app.Fragment
import com.rxjava.practice.ItemRunnable

open class BaseItemFragment : Fragment() {

    protected lateinit var mItemRunnable: ItemRunnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            val clazz = args.getSerializable(IntentCreator.KEY_EXTRA_EXECUTOR) as Class<*>
            if (ItemRunnable::class.java.isAssignableFrom(clazz)) {
                mItemRunnable = clazz.newInstance() as ItemRunnable
            }
        }
    }
}