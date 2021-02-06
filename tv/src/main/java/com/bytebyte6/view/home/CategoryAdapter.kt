package com.bytebyte6.view.home

import android.view.ViewGroup
import com.bytebyte6.common.randomColorByNightMode
import com.bytebyte6.data.model.Category
import com.bytebyte6.data.model.CategoryDiff
import com.bytebyte6.utils.BaseListAdapter
import com.bytebyte6.view.adapter.CardViewHolder

class CategoryAdapter : BaseListAdapter<Category, CardViewHolder>(CategoryDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)

        holder.apply {
            //重建后的recyclerview Item是没有transName的 所以在onBind要重新赋值一遍 动画效果才会有~~
            itemView.transitionName = item.category
            tvTitle.text = item.category
            tvBody.text = item.color.toString()
            ivIcon.setImageResource(randomImage())
            cardView.apply {
                strokeWidth = 0
                strokeColor = 0
                radius = 10f
                setCardBackgroundColor(item.color)
            }
        }
    }
}