package com.rxjava.operator

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rxjava.operator.p1_basic_usage.*
import com.rxjava.operator.p2_thread_scheduler.*
import com.rxjava.operator.p3_operator.t1_create.*
import com.rxjava.operator.p3_operator.t2_transform.*
import com.rxjava.operator.p3_operator.t3_combine.t1_combine_observable.*
import com.rxjava.operator.p3_operator.t3_combine.t2_combine_event.*
import com.rxjava.operator.p3_operator.t3_combine.Demo2_Count
import com.rxjava.operator.p3_operator.t4_condition_boolean.*
import com.rxjava.operator.p3_operator.t5_back_pressure.Demo1_Flowable
import com.rxjava.operator.p3_operator.t5_back_pressure.async.*
import com.rxjava.operator.p3_operator.t5_back_pressure.strategy.*
import com.rxjava.operator.p3_operator.t5_back_pressure.sync.*
import com.rxjava.operator.p3_operator.t6_filter.codition.*
import com.rxjava.operator.p3_operator.t6_filter.size.*
import com.rxjava.operator.p3_operator.t6_filter.position.*
import com.rxjava.operator.p3_operator.t6_filter.time.*

class OperatorListActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: ListAdapter
    private lateinit var mModelList: ArrayList<ItemModel>

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OperatorListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator_list)
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
        mModelList.add(ItemModel(R.string.item_create_special_create, Demo6_SpecialCreate::class.java))
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
        mModelList.add(ItemModel(R.string.item_flowable_sync_3, Demo3_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_4, Demo4_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_5, Demo5_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_6, Demo6_FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_1, Demo1_FlowableAsync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_2, Demo2_FlowableAsync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_3, Demo3_FlowableAsync::class.java))

        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_error, Demo1_Error::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_missing, Demo2_Missing::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_buffer, Demo3_Buffer::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_drop, Demo4_Drop::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_latest, Demo5_Latest::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_flowable_interval, Demo6_FlowableInterval::class.java))

        mModelList.add(ItemModel(R.string.item_title_filter, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_filter_operator, Demo1_Filter::class.java))
        mModelList.add(ItemModel(R.string.item_filter_of_type_operator, Demo2_OfType::class.java))
        mModelList.add(ItemModel(R.string.item_filter_skip_index_operator, Demo3_SkipIndex::class.java))
        mModelList.add(ItemModel(R.string.item_filter_skip_time_operator, Demo4_SkipTime::class.java))
        mModelList.add(ItemModel(R.string.item_filter_distinct, Demo5_Distinct::class.java))
        mModelList.add(ItemModel(R.string.item_filter_distinct_until_changed, Demo6_DistinctUntilChanged::class.java))
        mModelList.add(ItemModel(R.string.item_filter_take, Demo1_Take::class.java))
        mModelList.add(ItemModel(R.string.item_filter_take_last, Demo2_TakeLast::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_first, Demo1_ThrottleFirst::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_last, Demo2_ThrottleLast::class.java))
        mModelList.add(ItemModel(R.string.item_filter_sample, Demo3_Sample::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_with_time_out, Demo4_ThrottleWithTimeOut::class.java))
        mModelList.add(ItemModel(R.string.item_filter_debounce, Demo5_Debounce::class.java))
        mModelList.add(ItemModel(R.string.item_filter_first_and_last_element, Demo1_FirstOrLastElement::class.java))
        mModelList.add(ItemModel(R.string.item_filter_element_at, Demo2_ElementAt::class.java))
        mModelList.add(ItemModel(R.string.item_filter_element_at_or_error, Demo3_ElementAtOrError::class.java))

        return mModelList
    }
}