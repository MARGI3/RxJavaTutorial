package com.rxjava.practice.demo.demo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rxjava.practice.R
import com.rxjava.practice.intentcreator.BaseItemFragment

class EventBlockFragment : BaseItemFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_event_block, container, false)
    }

}