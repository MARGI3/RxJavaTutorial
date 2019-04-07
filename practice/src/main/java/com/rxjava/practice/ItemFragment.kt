package com.rxjava.practice

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ItemFragment : Fragment(), View.OnClickListener {

    private lateinit var mExecuteButton: Button
    private lateinit var mTriggerButton: Button
    private lateinit var mItemRunnable: ItemRunnable

    companion object {

        private const val KEY_EXECUTOR = "argument.executor"

        @JvmStatic
        fun newInstance(clazz: Class<ItemRunnable>): ItemFragment {
            val itemFragment = ItemFragment()
            val arguments = Bundle()
            arguments.putSerializable(KEY_EXECUTOR, clazz)
            itemFragment.arguments = arguments
            return itemFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            val clazz = args.getSerializable(KEY_EXECUTOR) as Class<*>
            if (ItemRunnable::class.java.isAssignableFrom(clazz)) {
                mItemRunnable = clazz.newInstance() as ItemRunnable
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mExecuteButton = view.findViewById(R.id.execute)
        mTriggerButton = view.findViewById(R.id.trigger)
        mExecuteButton.setOnClickListener(this)
        if (mItemRunnable is ITrigger) {
            mTriggerButton.visibility = View.VISIBLE
            mTriggerButton.setOnClickListener(this)
        } else {
            mTriggerButton.visibility = View.GONE
            mTriggerButton.setOnClickListener(null)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.execute -> mItemRunnable.run()
            R.id.trigger -> if (mItemRunnable is ITrigger) {
                (mItemRunnable as ITrigger).onTrigger(v)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mItemRunnable is Cancelable) {
            (mItemRunnable as Cancelable).cancel()
        }
    }
}