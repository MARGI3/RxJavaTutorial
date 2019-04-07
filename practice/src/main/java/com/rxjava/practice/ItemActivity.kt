package com.rxjava.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.v7.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    companion object {

        private const val KEY_EXTRA_CLASS = "key.extra.class"
        private const val KEY_EXTRA_TITLE = "key.extra.title"

        fun createIntent(context: Context, clazz: Class<out ItemRunnable>, @IntegerRes title: Int): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(KEY_EXTRA_CLASS, clazz)
            intent.putExtra(KEY_EXTRA_TITLE, title)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val clazz = intent.getSerializableExtra(KEY_EXTRA_CLASS) as Class<ItemRunnable>
        val titleRes = intent.getIntExtra(KEY_EXTRA_TITLE, 0)
        if (titleRes > 0) {
            setTitle(titleRes)
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame, ItemFragment.newInstance(clazz))
        fragmentTransaction.commit()
    }
}