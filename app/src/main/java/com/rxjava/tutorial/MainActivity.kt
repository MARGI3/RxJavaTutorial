package com.rxjava.tutorial

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.rxjava.operator.OperatorListActivity
import com.rxjava.practice.PracticeListActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mOperatorBtn: Button
    private lateinit var mPracticeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mOperatorBtn = findViewById(R.id.operator)
        mPracticeBtn = findViewById(R.id.practice)
        mOperatorBtn.setOnClickListener(this)
        mPracticeBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.operator -> startActivity(OperatorListActivity.createIntent(this))
            R.id.practice -> startActivity(PracticeListActivity.createIntent(this))
        }
    }
}
