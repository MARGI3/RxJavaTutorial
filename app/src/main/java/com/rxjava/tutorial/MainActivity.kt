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
import com.rxjava.tutorial.p3_operator.t3_combine.Demo2_Count
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo10_Amb
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo11_DefaultIfEmpty
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo1_All
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo2_TakeWhile
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo3_SkipWhile
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo4_TakeUntil
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo5_TakeUntil2
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo6_SkipUntil
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo7_SequenceEqual
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo8_Contains
import com.rxjava.tutorial.p3_operator.t4_condition_boolean.Demo9_IsEmpty
import com.rxjava.tutorial.p3_operator.t5_back_pressure.Demo1_Flowable
import com.rxjava.tutorial.p3_operator.t5_back_pressure.sync.Demo1_FlowableSync
import com.rxjava.tutorial.p3_operator.t5_back_pressure.async.Demo1_FlowableAsync
import com.rxjava.tutorial.p3_operator.t5_back_pressure.async.Demo2_FlowableAsync
import com.rxjava.tutorial.p3_operator.t5_back_pressure.sync.Demo2_FlowableSync

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

        mModelList.add(ItemModel(R.string.item_title_condition_boolean, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_all_operator, Demo1_All::class.java))
        mModelList.add(ItemModel(R.string.item_take_while_operator, Demo2_TakeWhile::class.java))
        mModelList.add(ItemModel(R.string.item_skip_while_operator, Demo3_SkipWhile::class.java))
        mModelList.add(ItemModel(R.string.item_take_until_operator, Demo4_TakeUntil::class.java))
        mModelList.add(ItemModel(R.string.item_take_until_operator_2, Demo5_TakeUntil2::class.java))
        mModelList.add(ItemModel(R.string.item_skip_until_operator, Demo6_SkipUntil::class.java))
        mModelList.add(ItemModel(R.string.item_sequence_equal_operator, Demo7_SequenceEqual::class.java))
        mModelList.add(ItemModel(R.string.item_contains, Demo8_Contains::class.java))
        mModelList.add(ItemModel(R.string.item_is_empty, Demo9_IsEmpty::class.java))
        mModelList.add(ItemModel(R.string.item_amb_operator, Demo10_Amb::class.java))
        mModelList.add(ItemModel(R.string.item_default_if_empty, Demo11_DefaultIfEmpty::class.java))

        mModelList.add(ItemModel(R.string.item_title_back_pressure, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_usage, Demo1_Flowable::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_1, Demo1_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_2, Demo2_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_1, Demo1_FlowableAsync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_2, Demo2_FlowableAsync::class.java))

        return mModelList
    }
}
