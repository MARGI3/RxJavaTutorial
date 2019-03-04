package com.rxjava.tutorial

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ItemFragment: Fragment(), View.OnClickListener {

    private lateinit var mButton: Button
    private lateinit var mItemRunnable: ItemRunnable

    companion object {

        private const val KEY_DESC = "argument.desc"
        private const val KEY_EXECUTOR = "argument.executor"

        @JvmStatic
        fun newInstance(@StringRes descRes: Int, clazz: Class<ItemRunnable>) : ItemFragment {
            val itemFragment = ItemFragment()
            val arguments = Bundle()
            arguments.putInt(KEY_DESC, descRes)
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
        return inflater.inflate(R.layout.item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mButton = view.findViewById(R.id.execute)
        mButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.execute -> mItemRunnable.run()
        }
    }
}