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
import com.rxjava.tutorial.p3_operator.t1_create.*
import com.rxjava.tutorial.p3_operator.t2_change.Demo1_Map
import com.rxjava.tutorial.p3_operator.t2_change.Demo2_FlatMap
import com.rxjava.tutorial.p3_operator.t2_change.Demo3_ConcatMap
import com.rxjava.tutorial.p3_operator.t2_change.Demo4_Buffer

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

        mModelList.add(ItemModel(R.string.item_create_demo_1, Demo1_Create::class.java))
        mModelList.add(ItemModel(R.string.item_create_demo_2, Demo2_Create::class.java))
        mModelList.add(ItemModel(R.string.item_create_just, Demo3_Just::class.java))
        mModelList.add(ItemModel(R.string.item_create_from_array, Demo4_FromArray::class.java))
        mModelList.add(ItemModel(R.string.item_create_from_iterable, Demo5_FromIterable::class.java))
        mModelList.add(ItemModel(R.string.item_create_special_create, Demo6_SpeicalCreate::class.java))
        mModelList.add(ItemModel(R.string.item_create_defer, Demo7_Defer::class.java))
        mModelList.add(ItemModel(R.string.item_create_timer, Demo8_Timer::class.java))
        mModelList.add(ItemModel(R.string.item_create_interval, Demo9_Interval::class.java))
        mModelList.add(ItemModel(R.string.item_create_interval_range, Demo10_IntervalRange::class.java))
        mModelList.add(ItemModel(R.string.item_create_range, Demo11_Range::class.java))
        mModelList.add(ItemModel(R.string.item_create_range_long, Demo12_RangeLong::class.java))

        mModelList.add(ItemModel(R.string.item_map_demo1, Demo1_Map::class.java))
        mModelList.add(ItemModel(R.string.item_map_flat_map, Demo2_FlatMap::class.java))
        mModelList.add(ItemModel(R.string.item_map_concat_map, Demo3_ConcatMap::class.java))
        mModelList.add(ItemModel(R.string.item_buffer, Demo4_Buffer::class.java))

        return mModelList
    }
}
