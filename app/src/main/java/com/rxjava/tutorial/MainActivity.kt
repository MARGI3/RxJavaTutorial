package com.rxjava.tutorial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rxjava.tutorial.p1_basic_usage.BasicUsageDemo
import com.rxjava.tutorial.p1_basic_usage.BasicUsageDemo2
import com.rxjava.tutorial.p2_thread_scheduler.SchedulerDemo
import com.rxjava.tutorial.p2_thread_scheduler.SchedulerDemo2

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
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun buildList(): List<ItemModel> {
        mModelList = arrayListOf()
        mModelList.add(ItemModel(R.string.item_basic_usage, BasicUsageDemo::class.java))
        mModelList.add(ItemModel(R.string.item_basic_usage2, BasicUsageDemo2::class.java))
        mModelList.add(ItemModel(R.string.item_thread_scheduler, SchedulerDemo::class.java))
        mModelList.add(ItemModel(R.string.item_thread_scheduler2, SchedulerDemo2::class.java))
        return mModelList
    }
}
