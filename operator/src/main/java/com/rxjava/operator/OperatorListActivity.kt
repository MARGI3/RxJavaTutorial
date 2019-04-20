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
import com.rxjava.operator.p3_operator.t3_combine.Demo2Count
import com.rxjava.operator.p3_operator.t4_condition_boolean.*
import com.rxjava.operator.p3_operator.t5_back_pressure.Demo1Flowable
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
        mModelList.add(ItemModel(R.string.item_create_demo_1, Demo1Create::class.java))
        mModelList.add(ItemModel(R.string.item_create_demo_2, Demo2Create::class.java))
        mModelList.add(ItemModel(R.string.item_create_just, Demo3Just::class.java))
        mModelList.add(ItemModel(R.string.item_create_from_array, Demo4FromArray::class.java))
        mModelList.add(ItemModel(R.string.item_create_from_iterable, Demo5FromIterable::class.java))
        mModelList.add(ItemModel(R.string.item_create_special_create, Demo6SpecialCreate::class.java))
        mModelList.add(ItemModel(R.string.item_create_defer, Demo7Defer::class.java))
        mModelList.add(ItemModel(R.string.item_create_timer, Demo8Timer::class.java))
        mModelList.add(ItemModel(R.string.item_create_interval, Demo9Interval::class.java))
        mModelList.add(ItemModel(R.string.item_create_interval_range, Demo10IntervalRange::class.java))
        mModelList.add(ItemModel(R.string.item_create_range, Demo11Range::class.java))
        mModelList.add(ItemModel(R.string.item_create_range_long, Demo12RangeLong::class.java))

        mModelList.add(ItemModel(R.string.item_title_transform, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_map_demo1, Demo1Map::class.java))
        mModelList.add(ItemModel(R.string.item_map_flat_map, Demo2FlatMap::class.java))
        mModelList.add(ItemModel(R.string.item_map_concat_map, Demo3ConcatMap::class.java))
        mModelList.add(ItemModel(R.string.item_buffer, Demo4Buffer::class.java))

        mModelList.add(ItemModel(R.string.item_title_combine, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_combine_concat, Demo1Concat::class.java))
        mModelList.add(ItemModel(R.string.item_combine_concat_delay_error, Demo2ConcatDelayError::class.java))
        mModelList.add(ItemModel(R.string.item_combine_merge, Demo3Merge::class.java))
        mModelList.add(ItemModel(R.string.item_combine_merge_delay_error, Demo4MergeDelayError::class.java))
        mModelList.add(ItemModel(R.string.item_combine_zip, Demo1Zip::class.java))
        mModelList.add(ItemModel(R.string.item_combine_combine_latest, Demo2CombineLatest::class.java))
        mModelList.add(ItemModel(R.string.item_combine_reduce, Demo3Reduce::class.java))
        mModelList.add(ItemModel(R.string.item_combine_collect, Demo4Collect::class.java))

        mModelList.add(ItemModel(R.string.item_title_special_operator, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_start_with_operator, Demo5StartWith::class.java))
        mModelList.add(ItemModel(R.string.item_count_operator, Demo2Count::class.java))

        mModelList.add(ItemModel(R.string.item_title_condition_boolean, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_all_operator, Demo1All::class.java))
        mModelList.add(ItemModel(R.string.item_take_while_operator, Demo2TakeWhile::class.java))
        mModelList.add(ItemModel(R.string.item_skip_while_operator, Demo3SkipWhile::class.java))
        mModelList.add(ItemModel(R.string.item_take_until_operator, Demo4TakeUntil::class.java))
        mModelList.add(ItemModel(R.string.item_take_until_operator_2, Demo5TakeUntil2::class.java))
        mModelList.add(ItemModel(R.string.item_skip_until_operator, Demo6SkipUntil::class.java))
        mModelList.add(ItemModel(R.string.item_sequence_equal_operator, Demo7SequenceEqual::class.java))
        mModelList.add(ItemModel(R.string.item_contains, Demo8Contains::class.java))
        mModelList.add(ItemModel(R.string.item_is_empty, Demo9IsEmpty::class.java))
        mModelList.add(ItemModel(R.string.item_amb_operator, Demo10Amb::class.java))
        mModelList.add(ItemModel(R.string.item_default_if_empty, Demo11DefaultIfEmpty::class.java))

        mModelList.add(ItemModel(R.string.item_title_back_pressure, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_usage, Demo1Flowable::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_1, Demo1FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_2, Demo2FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_3, Demo3FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_4, Demo4FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_5, Demo5FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_sync_6, Demo6FlowableSync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_1, Demo1FlowableAsync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_2, Demo2FlowableAsync::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_async_3, Demo3FlowableAsync::class.java))

        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_error, Demo1Error::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_missing, Demo2Missing::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_buffer, Demo3Buffer::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_drop, Demo4Drop::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_strategy_latest, Demo5Latest::class.java))
        mModelList.add(ItemModel(R.string.item_flowable_back_pressure_flowable_interval, Demo6FlowableInterval::class.java))

        mModelList.add(ItemModel(R.string.item_title_filter, ItemRunnable::class.java))
        mModelList.add(ItemModel(R.string.item_filter_operator, Demo1Filter::class.java))
        mModelList.add(ItemModel(R.string.item_filter_of_type_operator, Demo2OfType::class.java))
        mModelList.add(ItemModel(R.string.item_filter_skip_index_operator, Demo3SkipIndex::class.java))
        mModelList.add(ItemModel(R.string.item_filter_skip_time_operator, Demo4SkipTime::class.java))
        mModelList.add(ItemModel(R.string.item_filter_distinct, Demo5Distinct::class.java))
        mModelList.add(ItemModel(R.string.item_filter_distinct_until_changed, Demo6DistinctUntilChanged::class.java))
        mModelList.add(ItemModel(R.string.item_filter_take, Demo1Take::class.java))
        mModelList.add(ItemModel(R.string.item_filter_take_last, Demo2TakeLast::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_first, Demo1ThrottleFirst::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_last, Demo2ThrottleLast::class.java))
        mModelList.add(ItemModel(R.string.item_filter_sample, Demo3Sample::class.java))
        mModelList.add(ItemModel(R.string.item_filter_throttle_with_time_out, Demo4ThrottleWithTimeOut::class.java))
        mModelList.add(ItemModel(R.string.item_filter_debounce, Demo5Debounce::class.java))
        mModelList.add(ItemModel(R.string.item_filter_first_and_last_element, Demo1FirstOrLastElement::class.java))
        mModelList.add(ItemModel(R.string.item_filter_element_at, Demo2ElementAt::class.java))
        mModelList.add(ItemModel(R.string.item_filter_element_at_or_error, Demo3ElementAtOrError::class.java))

        return mModelList
    }
}
