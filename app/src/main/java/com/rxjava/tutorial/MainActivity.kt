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
import com.rxjava.tutorial.p3_operator.t2_transform.Demo1_Map
import com.rxjava.tutorial.p3_operator.t2_transform.Demo2_FlatMap
import com.rxjava.tutorial.p3_operator.t2_transform.Demo3_ConcatMap
import com.rxjava.tutorial.p3_operator.t2_transform.Demo4_Buffer
import com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable.Demo1_Concat
import com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable.Demo2_ConcatDelayError
import com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable.Demo3_Merge
import com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable.Demo4_MergeDelayError
import com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event.Demo1_Zip
import com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event.Demo2_CombineLatest
import com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event.Demo3_Reduce
import com.rxjava.tutorial.p3_operator.t3_combine.t2_combine_event.Demo4_Collect
import com.rxjava.tutorial.p3_operator.t3_combine.t1_combine_observable.Demo5_StartWith
import com.rxjava.tutorial.p3_operator.Demo2_Count

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

        mModelList.add(ItemModel(R.string.item_title_basic, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_basic_usage, BasicUsageDemo::class.java))
        mModelList.add(ItemModel(R.string.item_basic_usage2, BasicUsageDemo2::class.java))
        mModelList.add(ItemModel(R.string.item_thread_scheduler, SchedulerDemo::class.java))
        mModelList.add(ItemModel(R.string.item_thread_scheduler2, SchedulerDemo2::class.java))

        mModelList.add(ItemModel(R.string.item_title_create, ItemRunnable::class.java))
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

        mModelList.add(ItemModel(R.string.item_title_transform, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_map_demo1, Demo1_Map::class.java))
        mModelList.add(ItemModel(R.string.item_map_flat_map, Demo2_FlatMap::class.java))
        mModelList.add(ItemModel(R.string.item_map_concat_map, Demo3_ConcatMap::class.java))
        mModelList.add(ItemModel(R.string.item_buffer, Demo4_Buffer::class.java))

        mModelList.add(ItemModel(R.string.item_title_combine, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_combine_concat, Demo1_Concat::class.java))
        mModelList.add(ItemModel(R.string.item_combine_concat_delay_error, Demo2_ConcatDelayError::class.java))
        mModelList.add(ItemModel(R.string.item_combine_merge, Demo3_Merge::class.java))
        mModelList.add(ItemModel(R.string.item_combine_merge_delay_error, Demo4_MergeDelayError::class.java))
        mModelList.add(ItemModel(R.string.item_combine_zip, Demo1_Zip::class.java))
        mModelList.add(ItemModel(R.string.item_combine_combine_latest, Demo2_CombineLatest::class.java))
        mModelList.add(ItemModel(R.string.item_combine_reduce, Demo3_Reduce::class.java))
        mModelList.add(ItemModel(R.string.item_combine_collect, Demo4_Collect::class.java))

        mModelList.add(ItemModel(R.string.item_title_special_operator, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_start_with_operator, Demo5_StartWith::class.java))
        mModelList.add(ItemModel(R.string.item_count_operator, Demo2_Count::class.java))

        return mModelList
    }
}
