package com.rxjava.tutorial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rxjava.tutorial.basic_usage.BasicUsageDemo

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: ListAdapter
    private lateinit var mModelList: ArrayList<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler)
        mListAdapter = ListAdapter(buildList())
        mRecyclerView.adapter = mListAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun buildList(): List<ItemModel> {
        mModelList = arrayListOf()
        mModelList.add(ItemModel(R.string.item_basic_usage, BasicUsageDemo::class.java))

        return mModelList
    }
}
