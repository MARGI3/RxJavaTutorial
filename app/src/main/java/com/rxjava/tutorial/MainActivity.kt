package com.rxjava.tutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.rxjava.operator.OperatorListActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mOperatorBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mOperatorBtn = findViewById(R.id.operator)
        mOperatorBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.operator -> startActivity(OperatorListActivity.createIntent(this))
        }
    }
}
