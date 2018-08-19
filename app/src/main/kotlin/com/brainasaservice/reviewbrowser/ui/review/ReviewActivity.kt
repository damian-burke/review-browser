package com.brainasaservice.reviewbrowser.ui.review

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.SeekBar
import com.brainasaservice.api.review.ReviewSortBy
import com.brainasaservice.api.review.ReviewSortDirection
import com.brainasaservice.reviewbrowser.R
import com.brainasaservice.reviewbrowser.ui.ViewModelActivity
import com.brainasaservice.reviewbrowser.ui.review.list.ReviewAdapter
import kotlinx.android.synthetic.main.activity_review.recyclerView
import kotlinx.android.synthetic.main.activity_review.seekBarRating
import kotlinx.android.synthetic.main.activity_review.textOnlyWithText
import kotlinx.android.synthetic.main.activity_review.textPage
import kotlinx.android.synthetic.main.activity_review.textRating
import kotlinx.android.synthetic.main.activity_review.textSortBy
import kotlinx.android.synthetic.main.activity_review.textSortDirection
import timber.log.Timber

class ReviewActivity : ViewModelActivity<ReviewViewModel>(ReviewViewModel::class.java) {

    private val adapter: ReviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        setupRecyclerView()
        setupViewModelObserver()
        setupClickListener()
        setupChangeListener()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.addOnScrollListener((object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView?.layoutManager
                if (layoutManager != null && layoutManager is LinearLayoutManager) {

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                            firstVisibleItemPosition >= 0) {
                        recyclerView.post {
                            viewModel.loadNextPage()
                        }
                    }
                }
            }
        }))
    }

    private fun setupViewModelObserver() {
        viewModel.observeCountSetting().observe(this, Observer { value ->
            Timber.v("Count setting is now $value")
        })

        viewModel.observeDirectionSetting().observe(this, Observer { value ->
            val drawableId = when (value) {
                ReviewSortDirection.ASC.value -> R.drawable.ic_arrow_up
                else -> R.drawable.ic_arrow_down
            }
            val drawable = ContextCompat.getDrawable(this, drawableId)
            textSortDirection.setCompoundDrawablesWithIntrinsicBounds(0, drawableId, 0, 0)
        })

        viewModel.observeSortBySetting().observe(this, Observer { value ->
            val drawableId = when (value) {
                ReviewSortBy.DATE.value -> R.drawable.ic_date_range
                else -> R.drawable.ic_rate_review
            }
            textSortBy.setCompoundDrawablesWithIntrinsicBounds(0, drawableId, 0, 0)
        })

        viewModel.observeOnlyWithTextSetting().observe(this, Observer { value ->
            val drawableId = when (value) {
                true -> R.drawable.ic_message
                else -> R.drawable.ic_message_inactive
            }
            textOnlyWithText.setCompoundDrawablesWithIntrinsicBounds(0, drawableId, 0, 0)
        })

        viewModel.observeReviews().observe(this, Observer { list ->
            list?.let {
                adapter.items = it
            }
        })

        viewModel.observeRatingSetting().observe(this, Observer { value ->
            value?.let { rating ->
                seekBarRating.progress = rating
                val text = if (rating > 0) {
                    "Only show $rating-star reviews"
                } else {
                    "Showing all reviews"
                }
                textRating.text = text
            }
        })

        viewModel.observeRatingFilterEnabledSetting().observe(this, Observer { value ->
            value?.let {
                when(it) {
                    true -> seekBarRating.alpha = 1.0f
                    false -> seekBarRating.alpha = 0.6f
                }
            }
        })

        viewModel.observeReviewPage().observe(this, Observer { page ->
            page?.let { textPage.text = "- $it -" }
        })
    }

    private fun setupClickListener() {
        textSortDirection.setOnClickListener { viewModel.toggleSortDirection() }
        textSortBy.setOnClickListener { viewModel.toggleSortBy() }
        textOnlyWithText.setOnClickListener { viewModel.toggleOnlyWithText() }
    }

    private fun setupChangeListener() {
        seekBarRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) = Unit

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let { viewModel.setRating(it.progress) }
            }
        })
    }
}
