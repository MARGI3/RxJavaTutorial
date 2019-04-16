package com.rxjava.practice.intentcreator

import android.os.Bundle
import android.support.v4.app.Fragment
import com.rxjava.practice.ItemRunnable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseItemFragment : Fragment() {

    companion object {
        const val TAG = "RxJavaTutorial"
    }

    private var mCompositeDisposable: CompositeDisposable? = null
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

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }

    protected fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun clearDisposable() {
        mCompositeDisposable?.clear()
    }
}