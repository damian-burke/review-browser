package com.brainasaservice.reviewbrowser.ui.review.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brainasaservice.api.review.response.Review
import com.brainasaservice.reviewbrowser.R
import kotlinx.android.synthetic.main.review_item.view.textAuthor
import kotlinx.android.synthetic.main.review_item.view.textDate
import kotlinx.android.synthetic.main.review_item.view.textRating
import kotlinx.android.synthetic.main.review_item.view.textReview
import java.text.DateFormat
import java.text.SimpleDateFormat

class ReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Review) {
        view.textAuthor.text = item.reviewerName
        view.textDate.text = sdf.format(item.date)
        view.textReview.text = item.message
        view.textRating.text = "${item.rating.toInt()} / 5"
    }

    companion object {
        const val TYPE = 0
        val sdf: DateFormat = SimpleDateFormat.getDateInstance()

        fun create(parent: ViewGroup): ReviewViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
            return ReviewViewHolder(view)
        }
    }
}
