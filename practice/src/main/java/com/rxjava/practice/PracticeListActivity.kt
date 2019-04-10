package com.rxjava.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rxjava.practice.p1_polling.Demo1_Polling
import com.rxjava.practice.p1_polling.Demo2_FailedRetry
import com.rxjava.practice.p1_polling.Demo3_NestingRequest
import com.rxjava.practice.p1_polling.Demo4_Cache

class PracticeListActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: ListAdapter
    private lateinit var mModelList: ArrayList<ItemModel>

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, PracticeListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_list)
        mRecyclerView = findViewById(R.id.recycler)
        mListAdapter = ListAdapter(buildList())
        mRecyclerView.adapter = mListAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun buildList(): List<ItemModel> {
        mModelList = arrayListOf()
        mModelList.add(ItemModel(R.string.item_network_polling_request, Demo1_Polling::class.java))
        mModelList.add(ItemModel(R.string.item_network_failed_retry, Demo2_FailedRetry::class.java))
        mModelList.add(ItemModel(R.string.item_nesting_request, Demo3_NestingRequest::class.java))
        mModelList.add(ItemModel(R.string.item_cache, Demo4_Cache::class.java))
        return mModelList
    }

}