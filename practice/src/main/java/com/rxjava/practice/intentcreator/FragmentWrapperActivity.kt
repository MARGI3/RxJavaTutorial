package com.rxjava.practice.intentcreator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.rxjava.practice.R
import java.lang.IllegalArgumentException

class FragmentWrapperActivity : AppCompatActivity() {

    companion object {

        private const val KEY_EXTRA_CLASS = "key.extra.fragment.class"
        private const val KEY_EXTRA_ARGS = "key.extra.fragment.args"

        fun createIntent(context: Context, clazz: Class<out Fragment>, bundle: Bundle): Intent {
            val intent = Intent(context, FragmentWrapperActivity::class.java)
            intent.putExtra(KEY_EXTRA_CLASS, clazz.name)
            intent.putExtra(KEY_EXTRA_ARGS, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val fragmentName = intent.getStringExtra(KEY_EXTRA_CLASS)
        if (fragmentName.isEmpty()) {
            throw IllegalArgumentException("fragment name is empty")
        }
        val bundle = intent.getBundleExtra(KEY_EXTRA_ARGS)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = Fragment.instantiate(this, fragmentName, bundle)
        fragmentTransaction.add(R.id.frame, fragment)
        fragmentTransaction.commit()
    }
}