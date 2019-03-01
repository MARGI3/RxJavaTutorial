package com.rxjava.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    companion object {

        private val KEY_EXTRA = "key.extra"

        fun createIntent(context: Context, clazz: Class<out ItemRunnable>): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(KEY_EXTRA, clazz)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)
        val clazz = intent.getSerializableExtra(KEY_EXTRA) as Class<ItemRunnable>
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame, ItemFragment.newInstance(R.string.item_basic_usage, clazz))
        fragmentTransaction.commit()
    }
}