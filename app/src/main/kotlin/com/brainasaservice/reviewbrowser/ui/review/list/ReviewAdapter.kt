package com.brainasaservice.reviewbrowser.ui.review.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.brainasaservice.api.review.response.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewViewHolder>() {

    var items: List<Review> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.create(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}
