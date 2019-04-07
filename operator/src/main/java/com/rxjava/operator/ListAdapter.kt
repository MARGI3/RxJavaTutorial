package com.rxjava.operator

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListAdapter(list: List<ItemModel>) : RecyclerView.Adapter<ListAdapter.ItemHolder>() {

    private var mModelList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return mModelList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ItemHolder, position: Int) {
        holder.bind(mModelList[position])
    }

    class ItemHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        private var mText: TextView = parent.findViewById(R.id.item_title)

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false))
            }
        }

        fun bind(model: ItemModel) {
            val name = model.mClazz.simpleName
            if (ItemRunnable::class.java.simpleName == name) {
                mText.setBackgroundColor(Color.GRAY)
            } else {
                mText.setBackgroundColor(Color.WHITE)
                mText.setOnClickListener {
                    val context = it.context
                    context.startActivity(ItemActivity.createIntent(context, model.mClazz, model.mTitle))
                }
            }
            mText.setText(model.mTitle)

        }
    }
}